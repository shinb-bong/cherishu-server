package cherish.backend.item.repository;

import cherish.backend.item.model.Item;
import cherish.backend.member.model.Member;

import java.util.List;

public interface ItemLikeRepositoryCustom {
    List<Item> findItemLike(Member member);

    Long findItemLikeId(Long itemId, String email);
}
