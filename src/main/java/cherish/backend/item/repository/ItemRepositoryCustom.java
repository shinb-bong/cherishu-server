package cherish.backend.item.repository;

import cherish.backend.item.dto.ItemInfoResponseDto;
import cherish.backend.member.model.Member;

public interface ItemRepositoryCustom {
    ItemInfoResponseDto itemResponse(Long itemId, Member member);
}