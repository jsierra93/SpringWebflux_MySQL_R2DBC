package co.com.jsierra.webfluxmysql.services;

import co.com.jsierra.webfluxmysql.models.UserModels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DatabaseOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseOperations.class);

    @Autowired
    UsersRepository usersRepository;

    public Flux<UserModels> findUsers() {
        return usersRepository.findAll()
                .doOnNext(val -> LOGGER.info("doOnNext findUsers {}", val))
                .doOnComplete(() -> LOGGER.info(" Finalizo"));
    }


    public Mono<UserModels> findUsersById(String id) {
        Long idUser = Long.valueOf(id);
        return usersRepository.findById(idUser)
                .doOnNext(val -> LOGGER.info("doOnNext  findUsersById{}", val));
    }

    public Mono<UserModels> saveUser(UserModels newUser) {
        return usersRepository.save(newUser)
                .doOnNext(val -> LOGGER.info("doOnNext saveUser {}", val));
    }

    public Mono<UserModels> updateUser(UserModels update) {
        Mono<UserModels> result = usersRepository.findByUsername(update.getUsername())
                .flatMap(user -> {
                    update.setId(user.getId());
                 return usersRepository.save(update);
                })
                .doOnNext(val -> LOGGER.info("doOnNext {}", val))
                .doOnSuccess(x -> LOGGER.info("success {}", x));
        return result;
    }

    public Mono<Void> deleteUser(String id) {
        Long idUser = Long.valueOf(id);
        return usersRepository.deleteById(idUser);
    }

}
