package cherish.backend.item.service;

import cherish.backend.item.dto.*;
import cherish.backend.item.repository.ItemFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemFilterRepository itemFilterRepository;
    private final ItemSearchQueryDtoMapper itemMidMapper;

    public Page<ItemSearchDto.ResponseSearchItem> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {
        Page<ItemSearchQueryDto> content = itemFilterRepository.searchItem(searchCondition, pageable);
        List<ItemSearchDto.ResponseSearchItem> response = content.stream()
                .map(itemSearchQueryDto -> itemMidMapper.map(itemSearchQueryDto))
                .collect(Collectors.toList());

        return new PageImpl<>(response, pageable, content.getTotalElements());
    }
}
