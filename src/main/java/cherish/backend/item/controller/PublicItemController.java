package cherish.backend.item.controller;

import cherish.backend.auth.security.CurrentUser;
import cherish.backend.item.dto.ItemInfoViewDto;
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
            @RequestParam(required = false) String gender,
            @CurrentUser Member member, Pageable pageable) {
        ItemSearchCondition condition = new ItemSearchCondition();

        condition.setKeyword(keyword);
        if (categoryName != null && !categoryName.isEmpty()) {
            List<String> categories = List.of(categoryName.split(","));
            condition.setCategoryName(categories);
        }
        condition.setJobName(jobName);
        condition.setSituationName(situationName);
        condition.setGender(gender);

        return itemService.searchItem(condition, member, pageable);
    }

    @GetMapping("/{itemId}")
    public ItemInfoViewDto findItemInformation(@PathVariable Long itemId, @CurrentUser Member member) {
        return itemService.findItemInfo(itemId, member);
    }
}
