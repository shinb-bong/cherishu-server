package cherish.backend.item.controller;

import cherish.backend.auth.security.CurrentUser;
import cherish.backend.item.dto.ItemInfoResponseDto;
import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchResponseDto;
import cherish.backend.item.service.ItemService;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/item")
public class PublicItemController {

    private final ItemService itemService;

    @GetMapping("/search")
    public Page<ItemSearchResponseDto> searchItemWithFilter(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String jobName,
            @RequestParam(required = false) String situationName,
            @RequestParam(required = false) String emotionName,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            Pageable pageable) {

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setKeyword(keyword);
        if (categoryName != null && !categoryName.isEmpty()) {
            List<String> categories = List.of(categoryName.split(","));
            condition.setCategoryName(categories);
        }
        condition.setJobName(jobName);
        condition.setSituationName(situationName);
        condition.setEmotionName(emotionName);
        condition.setMinAge(minAge);
        condition.setMaxAge(maxAge);

        return itemService.searchItem(condition, pageable);
    }

    @GetMapping("/{itemId}")
    public ItemInfoResponseDto findItemInformation(@PathVariable Long itemId, @CurrentUser Member member) {
        itemService.increaseViews(itemId);
        return itemService.findItemInfo(itemId, member);
    }
}
