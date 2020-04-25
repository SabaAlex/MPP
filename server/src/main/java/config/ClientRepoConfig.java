package config;

import model.domain.Client;
import model.exceptions.MyException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.SortingRepository;
import repository.postgreSQL.ClientSQLRepository;

@Configuration
public class ClientRepoConfig {

    @Bean("ClientSQLRepository")
    SortingRepository<Long, Client> ClientSQLRepository() throws MyException {
        return new ClientSQLRepository();
    }


}
