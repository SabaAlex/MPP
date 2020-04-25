package config;

import model.exceptions.MyException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.postgreSQL.RentalSQLRepository;

@Configuration
public class RentalRepoConfig {

    @Bean("RentalSQLRepository")
    RentalSQLRepository RentalSQLRepository() throws MyException {
        return new RentalSQLRepository();
    }


}
