package cherish.backend.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Test
    public void 이메일_인증_확인(){
        //given
        String email = "cherishu.fullstack@gmail.com";
        //when
        String code = memberService.sendEmailCode(email);
        boolean success = memberService.validEmailCode(email, code);
        //then
        assertThat(success).isTrue();
    }
    @Test
    public void 이메일_인증_불일치_확인(){
        //given
        String email = "cherishu.fullstack@gmail.com";
        //when
        memberService.sendEmailCode(email);
        assertThrows(IllegalStateException.class,()->memberService.validEmailCode(email, "test"));
    }

    @Test
    public void 이메일_30초내에_재발송(){
        //given
        String email = "cherishu.fullstack@gmail.com";
        //when
        memberService.sendEmailCode(email);
        assertThrows(IllegalStateException.class,()->memberService.sendEmailCode(email) );
        //then
    }

    @Test
    public void 이메일_30초_후_재전송() throws InterruptedException {
        String email = "cherishu.fullstack@gmail.com";
        memberService.sendEmailCode(email);
        Thread.sleep(30000);
        String code = memberService.sendEmailCode(email);
        boolean success = memberService.validEmailCode(email, code);
        assertThat(success).isTrue();
    }


}