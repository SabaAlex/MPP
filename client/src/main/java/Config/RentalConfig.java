package Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import services.IRentalService;

@Configuration
public class RentalConfig {
    @Bean("IRentalService")
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IRentalService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/IRentalService");
        return rmiProxyFactoryBean;
    }
}
