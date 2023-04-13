package cherish.backend.member.controller;

import cherish.backend.auth.security.CurrentUser;
import cherish.backend.member.model.Member;
import cherish.backend.member.dto.ChangeInfoRequest;
import cherish.backend.member.dto.MemberInfoResponse;
import cherish.backend.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    // 회원 삭제
    @DeleteMapping("/delete")
    public void delete(@CurrentUser Member member){
        memberService.delete(member.getEmail());
    }

    // 회원 수정
    @PatchMapping("/change-info")
    public void changeInfo(@RequestBody @Valid ChangeInfoRequest request, @CurrentUser Member member){
        memberService.changeInfo(request.getNickName(), request.getJobName(), member);
    }

    // 내정보
    @GetMapping("/info")
    public MemberInfoResponse memberInfo(@CurrentUser Member member){
        return memberService.getInfo(member);
    }
}

