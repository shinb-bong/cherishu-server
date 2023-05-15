package cherish.backend.item.controller;

import cherish.backend.auth.security.CurrentUser;
import cherish.backend.item.dto.RecommendItemResponseDto;
import cherish.backend.item.service.RecommendItemService;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/main")
public class PublicRecommendItemController {
    private final RecommendItemService recommendItemService;

    @GetMapping
    public List<RecommendItemResponseDto> getRecommendItems(@CurrentUser Member member) {
        log.info("메인페이지 조회 = {}", member);
        return recommendItemService.getRecommendItem(member);
    }
}
