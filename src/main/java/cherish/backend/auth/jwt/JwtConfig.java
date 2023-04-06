package cherish.backend.auth.jwt;

import cherish.backend.common.util.DurationShortFormUtils;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Getter
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
@Configuration
public class JwtConfig {
    private final JwtProperties jwtProperties;
    private long accessTokenExpireMillis;
    private long refreshTokenExpireMillis;

    @Bean
    public Key key() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @PostConstruct
    public void init() {
        this.accessTokenExpireMillis = DurationShortFormUtils.convertShortFormToMillis(jwtProperties.getAccessTokenExpireTime());
        this.refreshTokenExpireMillis = DurationShortFormUtils.convertShortFormToMillis(jwtProperties.getRefreshTokenExpireTime());
    }
}
