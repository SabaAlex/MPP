package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"repository.postgreSQL.jpa", "Service", "UI"})
public class ProblemConfig {

}
