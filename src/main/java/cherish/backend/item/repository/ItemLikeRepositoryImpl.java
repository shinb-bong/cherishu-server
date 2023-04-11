package cherish.backend.item.repository;

import cherish.backend.item.model.Item;
import cherish.backend.member.model.Member;
import cherish.backend.member.model.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static cherish.backend.item.model.QItem.item;
import static cherish.backend.item.model.QItemLike.itemLike;

@RequiredArgsConstructor
public class ItemLikeRepositoryImpl implements ItemLikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<Item> findItemLike(Member member) {
        return queryFactory.select(item)
                .from(itemLike)
                .leftJoin(itemLike.item, item)
                .leftJoin(itemLike.member, QMember.member)
                .where(itemLike.member.eq(member))
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
