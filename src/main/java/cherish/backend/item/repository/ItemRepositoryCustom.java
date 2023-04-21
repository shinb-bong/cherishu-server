package cherish.backend.item.repository;

import cherish.backend.item.dto.ItemInfoResponseDto;
import cherish.backend.member.model.Member;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemInfoResponseDto> itemResponse(Long itemId, Member member);
}