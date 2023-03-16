package cherish.backend.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {

    @Bean
    public HikariConfig hikariConfig(SimpleHikariConfig simpleHikariConfig) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(simpleHikariConfig.getUsername());
        hikariConfig.setPassword(simpleHikariConfig.getPassword());
        hikariConfig.setDriverClassName(simpleHikariConfig.getDriverClassName());
        hikariConfig.setJdbcUrl(simpleHikariConfig.getJdbcUrl());
        hikariConfig.setSchema(simpleHikariConfig.getSchema());
        return hikariConfig;
    }

    @Primary
    @Bean
    public DataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }
}
