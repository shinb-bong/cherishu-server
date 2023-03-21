package cherish.backend.test;

import cherish.backend.common.service.RedisService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("test")
public class TestRestController {
    private final RedisService redisService;
    @GetMapping("excuteProcess/{processNum}")
    public String process(@PathVariable int processNum) throws InterruptedException {
        log.info("========================== Start Process -> Process Number: {}", processNum);
        Thread.sleep(20000);
        log.info("========================== End Process -> Process Number: {}", processNum);

        return "Process Success !!";
    }

    @GetMapping("/redis")
    public String testRedis() {
        String uid = UUID.randomUUID().toString().substring(0, 7);
        redisService.redisString(uid);
        return uid;
    }

}