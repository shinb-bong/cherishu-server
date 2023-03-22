package cherish.backend.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String setRedisCode(String key,String validCode) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key, validCode);
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