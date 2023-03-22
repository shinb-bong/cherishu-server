package cherish.backend.member.service;

import org.assertj.core.api.Assertions;
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


}