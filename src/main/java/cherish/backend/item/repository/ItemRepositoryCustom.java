package cherish.backend.item.repository;

import cherish.backend.item.dto.ItemInfoResponseDto;

public interface ItemRepositoryCustom {
    ItemInfoResponseDto itemResponse(Long itemId);
}