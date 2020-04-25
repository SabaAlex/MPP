package config;

import model.domain.Movie;
import model.validators.MovieValidator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repository.SortingRepository;
import services.IMovieService;
import services.MovieService;

import java.sql.SQLException;

@Configuration
public class MovieConfig implements ApplicationContextAware {
    private static ApplicationContext context;
    public static ApplicationContext getContext() {
        return context;
    }

    @Bean("IMovieService")
    RmiServiceExporter rmiServiceExporter() throws SQLException {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("IMovieService");
        rmiServiceExporter.setServiceInterface(IMovieService.class);
        rmiServiceExporter.setService(movieService());
        return rmiServiceExporter;
    }

    @Bean
    MovieService movieService() throws SQLException {
        SortingRepository<Long, Movie> movieRepo = (SortingRepository<Long, Movie>) context.getBean("MovieSQLRepository");
        return new MovieService(movieRepo, new MovieValidator());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
