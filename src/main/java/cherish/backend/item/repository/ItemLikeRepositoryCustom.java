package cherish.backend.item.repository;

import cherish.backend.item.model.Item;

import java.util.List;

public interface ItemLikeRepositoryCustom {
    List<Item> findItemLike(String email);

    Long findItemLikeId(Long itemId, String email);
}
