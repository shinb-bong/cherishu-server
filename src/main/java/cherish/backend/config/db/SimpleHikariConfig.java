package cherish.backend.config.db;

import lombok.Data;

@Data
public class SimpleHikariConfig {
    private String username;
    private String password;
    private String jdbcUrl;
    private String driverClassName;
    private String schema;
}
