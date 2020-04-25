package config;

import model.domain.Rental;
import model.validators.RentalValidator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repository.SortingRepository;
import services.IClientService;
import services.IMovieService;
import services.IRentalService;
import services.RentalService;

import java.sql.SQLException;

@Configuration
public class RentalConfig implements ApplicationContextAware {
    private static ApplicationContext context;
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Bean("IRentalService")
    RmiServiceExporter rmiServiceExporter() throws SQLException {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("IRentalService");
        rmiServiceExporter.setServiceInterface(IRentalService.class);
        rmiServiceExporter.setService(rentalService());
        return rmiServiceExporter;
    }

    @Bean
    RentalService rentalService() throws SQLException {
        IClientService clientService = context.getBean(IClientService.class);
        IMovieService movieService = context.getBean(IMovieService.class);
        SortingRepository<Long, Rental> rentalSQLRepository = (SortingRepository<Long, Rental>) context.getBean("RentalSQLRepository");

        return new RentalService(
                clientService, movieService, rentalSQLRepository, new RentalValidator());

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
