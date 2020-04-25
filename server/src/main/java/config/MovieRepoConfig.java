package config;


import model.domain.Movie;
import model.exceptions.MyException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.SortingRepository;
import repository.postgreSQL.MovieSQLRepository;

@Configuration
public class MovieRepoConfig {

    @Bean("MovieSQLRepository")
    SortingRepository<Long, Movie> MovieSQLRepository() throws MyException {
        return new MovieSQLRepository();
    }


}
