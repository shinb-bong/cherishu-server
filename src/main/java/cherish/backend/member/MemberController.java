package cherish.backend.member;

import cherish.backend.config.security.SecurityUser;
import cherish.backend.config.jwt.TokenInfo;
import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.dto.MemberLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody MemberFormDto memberFormDto){
        String savedEmail = memberService.join(memberFormDto);
        return new ResponseEntity<>(savedEmail, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();

        TokenInfo tokenInfo = memberService.login(email, password);
        return tokenInfo;
    }

    // 유틸 테스트
    // 객체로 받아오는 것
    @GetMapping("/info")
    public String info(@AuthenticationPrincipal SecurityUser member){
        return member.getMember().getEmail();
    }
}

