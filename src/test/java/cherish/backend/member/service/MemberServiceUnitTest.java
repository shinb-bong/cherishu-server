package cherish.backend.member.service;

import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.model.Member;
import cherish.backend.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Transactional
@ExtendWith(MockitoExtension.class)
public class MemberServiceUnitTest {

    @Mock
    private MemberRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberService memberService;

    @Test
    void 회원_가입(){
        //given
        given(repository.save(any())).willReturn(Member.builder().id(1L).name("testUser").build());
        MemberFormDto memberFormDto = createTestMember();
        //when
        Long joinId = memberService.join(memberFormDto);
        // then
        Assertions.assertThat(joinId).isEqualTo(1L);
    }

    private static MemberFormDto createTestMember() {
        return MemberFormDto.builder()
                .name("testUser")
                .email("test@naver.com")
                .password("123456")
                .nickname("test-man")
                .infoCheck(true)
                .gender(String.valueOf(Member.Gender.MALE))
                .build();
    }

}
