package cherish.backend.item.repository;

import cherish.backend.item.model.Item;
import cherish.backend.item.model.QItem;
import cherish.backend.item.model.QItemLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ItemLikeRepositoryImpl implements ItemLikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    QItemLike itemLike = QItemLike.itemLike;
    QItem item = QItem.item;
    @Override
    public List<Item> findItemLike(String email) {
        return queryFactory.select(item)
                .from(itemLike)
                .join(item)
                .where(itemLike.member.email.eq(email))
                .fetch();

    }

    @Override
    public Long findItemLikeId(Long itemId, String email) {
        return queryFactory.select(itemLike.id)
                .from(itemLike)
                .where(itemLike.member.email.eq(email),
                        itemLike.item.id.eq(itemId))
                .fetchOne();
    }
}
