package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchResponseDto;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/item")
public class PublicItemController {

    private final ItemService itemService;

    @GetMapping("/search")
    public ResponseEntity<Page<ItemSearchResponseDto.ResponseSearchItem>> searchItemWithFilter(
            @RequestParam(value = "keyword") String keyword,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setKeyword(encodedKeyword);

        Page<ItemSearchResponseDto.ResponseSearchItem> items = itemService.searchItem(condition, pageable);
        return ResponseEntity.ok(items);
    }

}
