package cherish.backend.item.service;

import cherish.backend.item.dto.*;
import cherish.backend.item.repository.ItemFilterRepository;
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

    public Page<ItemSearchResponseDto.ResponseSearchItem> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {
        Page<ItemSearchResponseDto.ResponseSearchItem> response = itemFilterRepository.searchItem(searchCondition, pageable);
        long total = response.getTotalElements();

        return new PageImpl<>(response.getContent(), pageable, total);
    }

}
