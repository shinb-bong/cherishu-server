package cherish.backend.common.service;

import cherish.backend.common.exception.RedisKeyNotFoundException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Redis에서 가져온 JSON string을 객체로 변환해주는 util method.
     * @param key Redis key
     * @param type 변환할 클래스
     * @return {@code T type} 클래스로 변환된 Redis value
     */
    public <T> T getValue(String key, Class<T> type) {
        if (!hasKey(key)) {
            log.error("키 {}는 존재하지 않습니다", key);
            throw new RedisKeyNotFoundException();
        }

        String jsonValue = (String) redisTemplate.opsForValue().get(key);
        try {
            return objectMapper.readValue(jsonValue, type);
        } catch (JacksonException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException();
        }
    }

    /**
     * Redis에 키와 값 세팅
     *
     * @param key Redis key
     * @param value Redis value object
     * @param second 지속시간 (초)
     */
    public void setRedisKeyValue(String key, Object value, int second) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, objectMapper.writeValueAsString(value), Duration.ofSeconds(second));
        } catch (JacksonException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException();
        }
    }
}
