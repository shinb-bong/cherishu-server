package cherish.backend.member.email;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Slf4j
@SpringBootTest
class SendEmailServiceTest {
    @Autowired
    SendEmailService emailService;

    @Test
    void AWS_SES_TEST(){
        //given
        String id = "cherishu.fullstack@gmail.com";
        //when
        String code = "";
        try {
            code = emailService.sendMessage(id, UUID.randomUUID().toString().substring(6));
        } catch (Exception e) {
            log.info("발송 오류 = {}", e.getMessage());
            throw new RuntimeException(e);
        }

        System.out.println("SendEmailServiceTest.AWS_SES_TEST");
        System.out.println(code);
    }

}