package cherish.backend.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {
    private static final String REFRESH_TOKEN_PREFIX = "_rtk_";
    private static final String EMAIL_CODE_PREFIX = "_code_";
    private static final String EMAIL_VERIFIED_PREFIX = "_vrf_";
    private static final String DAILY_COUNT_PREFIX = "_cnt_";

    private final RedisTemplate<String, String> redisTemplate;

    private boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean hasRefreshTokenKey(String email) {
        return hasKey(REFRESH_TOKEN_PREFIX + email);
    }

    public boolean hasEmailCodeKey(String email) {
        return hasKey(EMAIL_CODE_PREFIX + email);
    }

    public boolean hasVerifiedKey(String email) {
        return hasKey(EMAIL_VERIFIED_PREFIX + email);
    }

    public boolean hasCountKey(String email) {
        return hasKey(DAILY_COUNT_PREFIX + email);
    }

    private void set(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(time));
        log.info("set redis value for {} sec\n{} : {}", time, key, value);
    }

    public void setRefreshToken(String email, String value, long time) {
        this.set(REFRESH_TOKEN_PREFIX + email, value, time);
    }

    public void setEmailCode(String email, String value, long time) {
        this.set(EMAIL_CODE_PREFIX + email, value, time);
    }

    public void setEmailVerified(String email, boolean value, long time) {
        this.set(EMAIL_VERIFIED_PREFIX + email, value ? "y" : "n", time);
    }

    public void setDailyCount(String email, int value, long time) {
        this.set(REFRESH_TOKEN_PREFIX + email, String.valueOf(value), time);
    }

    private String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public String getRefreshToken(String email) {
        return get(REFRESH_TOKEN_PREFIX + email);
    }

    public String getEmailCode(String email) {
        return get(EMAIL_CODE_PREFIX + email);
    }

    public boolean isEmailVerified(String email) {
        return get(EMAIL_VERIFIED_PREFIX + email).equals("y");
    }

    public int getEmailCount(String email) {
        return Integer.parseInt(get(DAILY_COUNT_PREFIX + email));
    }

    private boolean deleteKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean deleteRefreshTokenKey(String email) {
        return deleteKey(REFRESH_TOKEN_PREFIX + email);
    }
}
