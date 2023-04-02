package cherish.backend.member;

import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.model.enums.Gender;
import cherish.backend.member.model.Member;
import cherish.backend.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@SpringBootTest
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Transactional
    @Test
    public void 회원가입(){
        //given
        MemberFormDto memberFormDto = getMemberFormDto();
        //when
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        Member savedMember = memberRepository.save(member);
        //then
        Assertions.assertThat(savedMember).isEqualTo(member);
        Assertions.assertThat(savedMember.getId()).isEqualTo(member.getId());
    }

    private static MemberFormDto getMemberFormDto() {
        MemberFormDto memberFormDto = new MemberFormDto("지수빈", "공주", "test@naver.com", "1234", true, "NONE", LocalDate.now(), "공주");
        return memberFormDto;
    }

    @Transactional
    @Test
    public void 비밀번호_변경(){
        MemberFormDto memberFormDto = getMemberFormDto();
        Member member = Member.createMember(memberFormDto,passwordEncoder);
        Member savedMember = memberRepository.save(member);
//        Long id = savedMember.getId();
        member.changePwd("12345",passwordEncoder);
        Assertions.assertThat(member.getPassword()).isEqualTo(savedMember.getPassword());
    }

}