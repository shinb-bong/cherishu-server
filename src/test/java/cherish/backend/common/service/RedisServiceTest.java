package cherish.backend.common.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    RedisService redisService;

    @Test
    void redis값저장_불러오기(){
        String uid = UUID.randomUUID().toString();
        String key = "test";
        redisService.setRedisCode(key,uid,10L);
        boolean success = redisService.validCode(key, uid);
        assertThat(success).isTrue();
    }

}