package cherish.backend.member;

import cherish.backend.member.dto.MemberFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody MemberFormDto memberFormDto){
        Member savedMember = memberRepository.save(Member.createMember(memberFormDto, passwordEncoder));
        return new ResponseEntity<>(savedMember.getId(), HttpStatus.CREATED);

    }
}
