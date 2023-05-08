package cherish.backend.common.config.cors;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Data
@ConfigurationProperties(prefix = CorsProperties.CORS_PREFIX)
public class CorsProperties {
    public static final String CORS_PREFIX = "cors";

    private Set<String> allowedMethods;
    private Set<String> allowedOrigins;
    private Set<String> allowedHeaders;
}
