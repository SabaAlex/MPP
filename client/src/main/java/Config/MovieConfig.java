package Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import services.IMovieService;

@Configuration
public class MovieConfig {
    @Bean("IMovieService")
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IMovieService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/IMovieService");
        return rmiProxyFactoryBean;
    }
}
