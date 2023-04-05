package cherish.backend.common.service;

import cherish.backend.common.exception.RedisKeyNotFoundException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public <T> T getValue(String key, Class<T> type) {
        String jsonValue = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(jsonValue)) {
            log.error("키 {}는 존재하지 않습니다", key);
            throw new RedisKeyNotFoundException();
        }
        try {
            return objectMapper.readValue(jsonValue, type);
        } catch (JacksonException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException();
        }
    }

    public void setRedisKeyValue(String key, Object value, int second) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, objectMapper.writeValueAsString(value), Duration.ofSeconds(second));
        } catch (JacksonException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException();
        }
    }

    public void setRedisCode(String key, String validCode, int second) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key)))
            throw new IllegalStateException(second + "초 내에 이메일을 재전송 할 수 없습니다.");
        this.setRedisKeyValue(key, validCode, second);
        log.info("input = {} ", validCode);
    }

    public boolean validCode(String key, String inputCode) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key)))
            throw new IllegalStateException("이메일 인증 코드를 발송한 내역이 없습니다.");
        String redisCode = this.getValue(key, String.class);
        if (!inputCode.equals(redisCode))
            throw new IllegalStateException("입력한 입력코드가 다릅니다.");
        return true;
    }
}
