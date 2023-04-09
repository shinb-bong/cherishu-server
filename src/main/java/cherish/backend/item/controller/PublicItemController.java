package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchResponseDto;
import cherish.backend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/item")
public class PublicItemController {

    private final ItemService itemService;

    @GetMapping("/search")
    public Page<ItemSearchResponseDto> searchItemWithFilter(
            @RequestParam(value = "keyword") String keyword, Pageable pageable) {

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setKeyword(keyword);
        return itemService.searchItem(condition, pageable);
    }

}
