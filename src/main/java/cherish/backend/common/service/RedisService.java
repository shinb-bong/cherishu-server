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

    private final RedisTemplate<String, Object> redisTemplate;

    public String redisString(String validCode) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set("validCode", validCode);
        String redis = (String)operations.get("validCode");
        log.info("input = {} ",redis);
        return redis;
    }
}