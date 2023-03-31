package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemDto;
import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/search")
    public ResponseEntity<Page<ItemDto.RequestSearchItem>> searchItemWithFilter(
            @RequestParam(value = "filterId", required = false) Long filterId,
            @RequestParam(value = "itemFilterName", required = false) String itemFilterName,
            @RequestParam(value = "itemCategoryParent", required = false) String itemCategoryParent,
            @RequestParam(value = "itemCategoryChildren", required = false) String itemCategoryChildren,
            @RequestParam(value = "itemJobParent", required = false) String itemJobParent,
            @RequestParam(value = "itemJobChildren", required = false) String itemJobChildren,
            @RequestParam(value = "itemName", required = false) String itemName,
            @RequestParam(value = "itemBrand", required = false) String itemBrand,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "target", required = false) String target,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setFilterId(filterId);
        condition.setItemFilterName(itemFilterName);
        condition.setItemCategoryParent(itemCategoryParent);
        condition.setItemCategoryChildren(itemCategoryChildren);
        condition.setItemJobParent(itemJobParent);
        condition.setItemJobChildren(itemJobChildren);
        condition.setItemName(itemName);
        condition.setItemBrand(itemBrand);
        condition.setKeyword(keyword);
        condition.setTarget(target);
        Page<ItemDto.RequestSearchItem> items = itemService.searchItem(condition, pageable);
        return ResponseEntity.ok(items);

    }

}
