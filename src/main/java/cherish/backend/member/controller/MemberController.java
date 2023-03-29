package cherish.backend.member.controller;

import cherish.backend.auth.security.SecurityUser;
import cherish.backend.auth.jwt.TokenInfo;
import cherish.backend.member.dto.*;
import cherish.backend.member.dto.email.EmailCodeValidationRequest;
import cherish.backend.member.dto.email.EmailRequest;
import cherish.backend.member.service.MemberService;
import jakarta.validation.Valid;
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
    @GetMapping("/is-member")
    public MemberEmailResponse isMember(@RequestParam("email") String email){
        boolean isMember = memberService.isMember(email);
        return new MemberEmailResponse(email,isMember);
    }
    // 비밀번호 수정
    // 기존비밀번호 확인이 체크가 되어있거나
    // 혹은 현재 로그인한 사용자가 바꾸길 원하는 이메일과 같은 경우
    @PostMapping("/change-password")
    public ResponseEntity changePwd(@RequestBody ChangePwdRequest request){
        memberService.changePwd(request.getEmail(),request.getPwd());
        return new ResponseEntity(HttpStatus.OK);
    }

    // 회원 수정
    @PatchMapping("/change-info")
    public ResponseEntity changeInfo(@RequestBody ChangeInfoRequest request){
        Long memberId = memberService.changeInfo(request.getNickName(), request.getJobName(), request.getMemberEmail());
        return ResponseEntity.status(HttpStatus.OK).body(memberId);
    }
    // 유틸 테스트
    // 객체로 받아오는 것
    @GetMapping("/info")
    public String info(@AuthenticationPrincipal SecurityUser member){
        return member.getMember().getEmail();
    }

    @PostMapping("/code-send")
    public ResponseEntity sendEmailCode(@RequestBody @Valid EmailRequest emailRequest){
        String code = memberService.sendEmailCode(emailRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(code);
    }

    @PostMapping("/code-valid")
    public ResponseEntity validEmailCode(@RequestBody @Valid EmailCodeValidationRequest request){
        memberService.validEmailCode(request.getEmail(), request.getCode());
        return new ResponseEntity(HttpStatus.OK);
    }
}

