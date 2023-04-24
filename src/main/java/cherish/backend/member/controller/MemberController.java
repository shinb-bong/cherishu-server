package cherish.backend.member.controller;

import cherish.backend.auth.security.CurrentUser;
import cherish.backend.member.dto.ChangeInfoRequest;
import cherish.backend.member.dto.MemberInfoResponse;
import cherish.backend.member.model.Member;
import cherish.backend.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 회원 삭제
    @DeleteMapping("/delete")
    public void delete(@CurrentUser Member member) {
        memberService.delete(member.getEmail());
    }

    // 회원 수정
    @PatchMapping("/info")
    public void changeInfo(@RequestBody @Valid ChangeInfoRequest request, @CurrentUser Member member) {
        // pw validation 컨트롤러 단에서 추가
        if (StringUtils.isNotBlank(request.getOldPassword())) {
            // old pw는 입력됐는데 new pw가 입력 안된 경우
            if (StringUtils.isBlank(request.getNewPassword())) {
                throw new IllegalArgumentException("새로운 비밀번호가 입력되지 않았습니다.");
            }
            // old pw와 new pw가 같은 경우
            if (request.getOldPassword().equals(request.getNewPassword())) {
                throw new IllegalArgumentException("기존 비밀번호와 새로운 비밀번호가 같습니다.");
            }

            Range<Integer> range = Range.between(8, 20);
            if (!range.contains(request.getOldPassword().length())
                || !range.contains(request.getNewPassword().length())) {
                throw new IllegalArgumentException("비밀번호 길이는 8~20자여야 합니다.");
            }
        }
        memberService.changeInfo(request, member);
    }

    // 내정보
    @GetMapping("/info")
    public MemberInfoResponse memberInfo(@CurrentUser Member member) {
        return memberService.getInfo(member);
    }
}

