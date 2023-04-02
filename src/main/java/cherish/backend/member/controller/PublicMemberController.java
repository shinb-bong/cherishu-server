package cherish.backend.member.controller;

import cherish.backend.auth.jwt.TokenInfo;
import cherish.backend.member.dto.ChangePwdRequest;
import cherish.backend.member.dto.MemberEmailResponse;
import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.dto.MemberLoginRequestDto;
import cherish.backend.member.dto.email.EmailCodeValidationRequest;
import cherish.backend.member.dto.email.EmailRequest;
import cherish.backend.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/public/member")
public class PublicMemberController {

    private final MemberService memberService;

    //회원 회원가입
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public String register(@RequestBody @Valid MemberFormDto memberFormDto){
        return memberService.join(memberFormDto);
    }

    // 회원 로그인
    @PostMapping("/login")
    public TokenInfo login(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        TokenInfo token = memberService.login(memberLoginRequestDto.getEmail(),
                memberLoginRequestDto.getPassword(),
                memberLoginRequestDto.isPersist());
        log.info("member_login = {}", memberLoginRequestDto.getEmail());
        return token;
    }

    // 비밀번호 찾기 (해당 아이디가 있는지 부터 검사)
    @GetMapping("/is-member")
    public MemberEmailResponse isMember(@RequestParam("email") @NotEmpty String email){
        boolean isMember = memberService.isMember(email);
        return new MemberEmailResponse(email,isMember);
    }
    // 비밀번호 수정
    // 기존비밀번호 확인이 체크가 되어있거나
    // 혹은 현재 로그인한 사용자가 바꾸길 원하는 이메일과 같은 경우
    @PostMapping("/change-password")
    public ResponseEntity changePwd(@RequestBody @Valid ChangePwdRequest request){
        memberService.changePwd(request.getEmail(),request.getPassword());
        return new ResponseEntity(HttpStatus.OK);
    }
    // 이메일 코드 발송
    @PostMapping("/code-send")
    public ResponseEntity sendEmailCode(@RequestBody @Valid EmailRequest emailRequest){
        String code = memberService.sendEmailCode(emailRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(code);
    }
    // 이메일 코드 검증
    @PostMapping("/code-valid")
    public ResponseEntity validEmailCode(@RequestBody @Valid EmailCodeValidationRequest request){
        memberService.validEmailCode(request.getEmail(), request.getCode());
        return new ResponseEntity(HttpStatus.OK);
    }
}
