package cherish.backend.test;

import cherish.backend.common.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Profile("local")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
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
        redisService.setRedisCode("test",uid,10L);
        return uid;
    }

    @GetMapping
    public String test() {
        return "ok";
    }

    @GetMapping("/date")
    public LocalDate testDate(@RequestParam("date") LocalDate date){
        return date;
    }
}
