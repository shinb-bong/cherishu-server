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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/item")
public class PublicItemController {

    private final ItemService itemService;

    @GetMapping("/search")
    public Page<ItemSearchResponseDto> searchItemWithFilter(ItemSearchCondition condition, @CurrentUser Member member, Pageable pageable) {
        return itemService.searchItem(condition, member, pageable);
    }

    @GetMapping("/{itemId}")
    public ItemInfoViewDto findItemInformation(@PathVariable Long itemId, @CurrentUser Member member) {
        return itemService.findItemInfo(itemId, member);
    }
}
