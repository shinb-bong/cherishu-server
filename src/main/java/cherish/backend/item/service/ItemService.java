package cherish.backend.item.service;

import cherish.backend.item.dto.ItemDto;
import cherish.backend.item.dto.ItemMapper;
import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchQueryDto;
import cherish.backend.item.model.Item;
import cherish.backend.item.repository.ItemFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemFilterRepository itemFilterRepository;
    private final ItemMapper itemMapper;

    public Page<ItemDto.RequestSearchItem> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {
        Page<ItemSearchQueryDto> searchResult = itemFilterRepository.searchItem(searchCondition, pageable);
        List<ItemDto.RequestSearchItem> items = searchResult.getContent()
                .stream()
                .map(itemMapper::searchQueryToRequestSearchDto)
                .collect(Collectors.toList());
        return new PageImpl<>(items, pageable, searchResult.getTotalElements());
    }

}

