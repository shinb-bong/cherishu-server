package cherish.backend.item.service;

import cherish.backend.item.dto.*;
import cherish.backend.item.model.Item;
import cherish.backend.item.repository.ItemFilterRepository;
import cherish.backend.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemFilterRepository itemFilterRepository;
    private final ItemRepository itemRepository;

    public Page<ItemSearchResponseDto> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {
        Page<ItemSearchResponseDto> response = itemFilterRepository.searchItem(searchCondition, pageable);
        long total = response.getTotalElements();

        return new PageImpl<>(response.getContent(), pageable, total);
    }

    public ItemInfoResponseDto findItemInfo(Long itemId) {
        return itemRepository.itemResponse(itemId);
    }

    @Transactional
    public void increaseViews(Long itemId) {
        Item item = itemRepository.findItemById(itemId);
        item.increaseViews();
        itemRepository.save(item);
    }
}
