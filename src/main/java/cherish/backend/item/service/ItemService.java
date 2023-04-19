package cherish.backend.item.service;

import cherish.backend.item.dto.*;
import cherish.backend.item.model.Item;
import cherish.backend.item.repository.ItemFilterRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.member.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    @PersistenceContext
    private EntityManager em;
    private final ItemFilterRepository itemFilterRepository;
    private final ItemRepository itemRepository;

    public Page<ItemSearchResponseDto> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {
        Page<ItemSearchResponseDto> response = itemFilterRepository.searchItem(searchCondition, pageable);
        long total = response.getTotalElements();

        return new PageImpl<>(response.getContent(), pageable, total);
    }

    public ItemInfoResponseDto findItemInfo(Long itemId, Member member) {
        increaseViews(itemId);
        List<ItemInfoResponseDto> itemResponses = itemRepository.itemResponse(itemId, member);

        // platform과 url을 그룹핑하여 반환
        Map<String, String> platforms = new HashMap<>();
        for (ItemInfoResponseDto itemResponse : itemResponses) {
            if (itemResponse.getPlatform() != null && itemResponse.getUrl() != null) {
                platforms.put(itemResponse.getPlatform(), itemResponse.getUrl());
            }
        }

        ItemInfoResponseDto itemInfoResponseDto = itemResponses.get(0);
        itemInfoResponseDto.setUrl(platforms.toString());
        itemInfoResponseDto.setPlatform(String.join(", ", platforms.keySet()));

        List<String> filterTags = itemResponses.stream()
                .map(ItemInfoResponseDto::getFilterTag)
                .distinct()
                .limit(2)
                .toList();

        itemInfoResponseDto.setFilterTag(filterTags.toString());

        return itemInfoResponseDto;
    }

    public void increaseViews(Long itemId) {
        Item item = em.find(Item.class, itemId);
        item.increaseViews();
    }
}
