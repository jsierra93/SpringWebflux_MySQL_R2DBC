package co.com.jsierra.webfluxmysql.controllers;

import co.com.jsierra.webfluxmysql.models.UserModels;
import co.com.jsierra.webfluxmysql.services.DatabaseOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    @Autowired
    DatabaseOperations databaseOperations;

    public Mono<ServerResponse> findUsers(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .body(databaseOperations.findUsers()
                                .log()
                        , UserModels.class);
    }

    public Mono<ServerResponse> findUserById(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(databaseOperations.findUsersById(serverRequest.pathVariable("id"))
                        .log(), UserModels.class);
    }

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        Mono<UserModels> newUser = serverRequest.bodyToMono(UserModels.class)
                .flatMap(user ->
                        databaseOperations.saveUser(user));

        return ServerResponse.ok()
                .body(newUser, UserModels.class);
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        Mono<UserModels> updateUser = serverRequest.bodyToMono(UserModels.class)
                .flatMap(user ->
                        databaseOperations.updateUser(user)
                );

        return ServerResponse.ok()
                .body(updateUser, UserModels.class);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(databaseOperations.deleteUser(serverRequest.pathVariable("id"))
                        , String.class);
    }
}
