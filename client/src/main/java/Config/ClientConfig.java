package Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import services.IClientService;

@Configuration
public class ClientConfig {
    @Bean("IClientService")
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IClientService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/IClientService");
        return rmiProxyFactoryBean;
    }
}
