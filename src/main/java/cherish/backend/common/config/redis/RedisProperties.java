package cherish.backend.common.config.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties("spring.data.redis")
public class RedisProperties {
    private String host;
    private int port;

}
