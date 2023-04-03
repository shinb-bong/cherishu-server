package cherish.backend.member.controller;

import cherish.backend.auth.security.SecurityUser;
import cherish.backend.member.dto.ChangeInfoRequest;
import cherish.backend.member.dto.MemberInfoResponse;
import cherish.backend.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    // 회원 삭제
    @DeleteMapping("/delete")
    public void delete(@RequestParam("email") @Email String email, @AuthenticationPrincipal SecurityUser securityUser){
        memberService.delete(email,securityUser.getMember().getEmail());
    }

    // 회원 수정
    @PatchMapping("/change-info")
    public Long changeInfo(@RequestBody @Valid ChangeInfoRequest request){
        return memberService.changeInfo(request.getNickName(), request.getJobName(), request.getEmail());
    }
    // 유틸 테스트
    // 객체로 받아오는 것
//    @GetMapping("/info")
//    public String info(@AuthenticationPrincipal SecurityUser member){
//        return member.getMember().getEmail();
//    }

    // 내정보
    @GetMapping("/info")
    public MemberInfoResponse memberInfo(@RequestParam("email") @Email String email){
        return memberService.getInfo(email);
    }
}

