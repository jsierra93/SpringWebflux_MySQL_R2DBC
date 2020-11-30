package co.com.jsierra.webfluxmysql;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
public class Config {
    @Value("${db.mysql.host}")
    private String host;

    @Value("${db.mysql.port}")
    private int port;

    @Value("${db.mysql.username}")
    private String user;

    @Value("${db.mysql.password}")
    private String password;

    @Value("${db.mysql.database}")
    private String database;
    @Bean
    public ConnectionFactory connectionFactory(){
         return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER,"mysql")
                .option(HOST,host)
                .option(PORT, port)
                .option(USER,user)
                .option(PASSWORD, password)
                .option(DATABASE, database)
                .build());
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    @Bean
    public DatabaseClient createDataBase(ConnectionFactory connection) {
        return DatabaseClient.create(connection);
        }

}
