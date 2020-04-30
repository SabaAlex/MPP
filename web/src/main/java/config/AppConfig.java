package config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import core.config.JPAConfig;

@Configuration
@ComponentScan("core")
@Import({JPAConfig.class})
@PropertySources({@PropertySource(value = "classpath:db.properties"),
})
public class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
