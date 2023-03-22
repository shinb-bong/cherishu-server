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

    public String setRedisCode(String key,String validCode) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Optional<String> isKey = Optional.ofNullable(operations.get(key));
        if (!isKey.isEmpty())
            throw new IllegalStateException("30초 내에 이메일을 재전송 할 수 없습니다.");
        operations.set(key, validCode, Duration.ofSeconds(30));
        log.info("input = {} ",validCode);
        return validCode;
    }
    public boolean validCode(String key,String inputCode) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String redisCode = operations.get(key);
        if (!inputCode.equals(redisCode))
            throw new IllegalStateException("입력한 입력코드가 다릅니다.");
        return true;
    }
}