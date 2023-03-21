package cherish.backend.common.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    RedisService redisService;

    @Test
    void redis값저장_불러오기(){
        String uid = UUID.randomUUID().toString();
        String getRedisValue = redisService.redisString(uid);
        Assertions.assertThat(uid).isEqualTo(getRedisValue);
    }



}