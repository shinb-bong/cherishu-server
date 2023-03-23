package cherish.backend.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String setRedisCode(String key,String validCode, Long time) {
        if (redisTemplate.hasKey(key))
            throw new IllegalStateException(time+"초 내에 이메일을 재전송 할 수 없습니다.");
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key, validCode, Duration.ofSeconds(time));
        log.info("input = {} ",validCode);
        return validCode;
    }
    public boolean validCode(String key,String inputCode) {
        if (!redisTemplate.hasKey(key))
            throw new IllegalStateException("이메일 인증 코드를 발송한 내역이 없습니다.");
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String redisCode = operations.get(key);
        if (!inputCode.equals(redisCode))
            throw new IllegalStateException("입력한 입력코드가 다릅니다.");
        return true;
    }
}