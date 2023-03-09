package cherish.backend.member;

import cherish.backend.member.dto.MemberFormDto;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberTest {

    @Autowired
    public MemberRepository memberRepository;

    @Autowired
    public PasswordEncoder passwordEncoder ;

    @Test
    public void 회원가입(){
        //given
        MemberFormDto memberFormDto = new MemberFormDto("지수빈", "공주", "test@naver.com", "1234", true, Gender.None, LocalDateTime.now(), "공주");
        //when
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        Member savedMember = memberRepository.save(member);
        //then
        Assertions.assertThat(savedMember).isEqualTo(member);
        Assertions.assertThat(savedMember.getId()).isEqualTo(member.getId());
    }

}