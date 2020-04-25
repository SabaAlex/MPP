package config;

import model.domain.Client;
import model.validators.ClientValidator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repository.SortingRepository;
import services.ClientService;
import services.IClientService;

import java.sql.SQLException;

@Configuration
public class ClientConfig implements ApplicationContextAware {
    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    @Bean("IClientService")
    RmiServiceExporter rmiServiceExporter() throws SQLException {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("IClientService");
        rmiServiceExporter.setServiceInterface(IClientService.class);
        rmiServiceExporter.setService(clientService());
        return rmiServiceExporter;
    }

    @Bean
    ClientService clientService() throws SQLException {
        SortingRepository<Long, Client> clientRepo = ((SortingRepository<Long, Client>) context.getBean("ClientSQLRepository"));
        return new ClientService(clientRepo,new ClientValidator());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
