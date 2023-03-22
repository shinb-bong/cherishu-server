package cherish.backend.member.controller;

import cherish.backend.auth.security.SecurityUser;
import cherish.backend.auth.jwt.TokenInfo;
import cherish.backend.member.service.MemberService;
import cherish.backend.member.dto.ChangePwdRequest;
import cherish.backend.member.dto.MemberEmailResponse;
import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.dto.MemberLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    //회원 회원가입
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public String register(@RequestBody MemberFormDto memberFormDto){
        return memberService.join(memberFormDto);
    }

    // 회원 로그인
    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        return memberService.login(memberLoginRequestDto.getEmail(),
                memberLoginRequestDto.getPassword(),
                memberLoginRequestDto.getIsPersist());
    }

    // 회원 삭제
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("email") String email,@AuthenticationPrincipal SecurityUser securityUser){
        memberService.delete(email,securityUser.getMember().getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 비밀번호 찾기 (해당 아이디가 있는지 부터 검사)
    @GetMapping("/isMember")
    public MemberEmailResponse isMember(@RequestParam("email") String email){
        Boolean isMember = memberService.isMember(email);
        return new MemberEmailResponse(email, isMember);
    }

    // 비밀번호 수정
    @PostMapping("/changePwd")
    public ResponseEntity changePwd(@RequestBody ChangePwdRequest request, @AuthenticationPrincipal SecurityUser securityUser){
        memberService.changePwd(request.getEmail(),request.getPwd(), securityUser.getMember().getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    // 회원 수정

    // 유틸 테스트
    // 객체로 받아오는 것
    @GetMapping("/info")
    public String info(@AuthenticationPrincipal SecurityUser member){
        return member.getMember().getEmail();
    }
}
