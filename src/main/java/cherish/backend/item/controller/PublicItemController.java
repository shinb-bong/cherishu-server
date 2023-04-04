package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchDto;
import cherish.backend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/item")
public class PublicItemController {

    private final ItemService itemService;

    @Profile("put-data")
    @GetMapping("/search")
    public ResponseEntity<Page<ItemSearchDto.ResponseSearchItem>> searchItemWithFilter(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "target") String target,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setKeyword(keyword);
        condition.setTarget(target);

        Page<ItemSearchDto.ResponseSearchItem> items = itemService.searchItem(condition, pageable);
        return ResponseEntity.ok(items);
    }

}
