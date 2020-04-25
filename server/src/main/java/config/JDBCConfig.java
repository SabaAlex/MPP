package config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * author: radu
 */

@Configuration
public class JDBCConfig {

    @Bean
    JdbcOperations jdbcOperations() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }

    @Bean
    DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        //TODO use env props (or property files)
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/MPP");
        basicDataSource.setUsername(System.getProperty("username"));
        basicDataSource.setPassword(System.getProperty("password"));
        basicDataSource.setInitialSize(2);

        return basicDataSource;
    }
}