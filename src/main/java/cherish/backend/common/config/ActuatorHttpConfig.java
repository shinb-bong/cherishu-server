package cherish.backend.common.config;

import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HTTP 요청 응답 기록
 * 최대 100개 HTTP 요청을 제공
 * 테스트시에만 임시로 사용 (기능의 제한이 많음.)
 * 운영 서비스에서는 핀포인트 이용하는 것
 */
@Configuration
public class ActuatorHttpConfig {

    @Bean
    public InMemoryHttpExchangeRepository httpExchangeRepository(){
        return new InMemoryHttpExchangeRepository();
    }
}
