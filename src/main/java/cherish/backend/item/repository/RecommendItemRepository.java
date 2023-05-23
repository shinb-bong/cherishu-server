package cherish.backend.item.repository;

import cherish.backend.item.dto.QRecommendItemQueryDto;
import cherish.backend.item.dto.RecommendItemQueryDto;
import cherish.backend.member.model.Member;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cherish.backend.board.model.QRecommend.recommend;
import static cherish.backend.item.model.QItem.item;
import static cherish.backend.item.model.QItemLike.itemLike;
import static cherish.backend.item.model.QRecommendItem.recommendItem;

@RequiredArgsConstructor
@Repository
public class RecommendItemRepository {
    private final JPAQueryFactory queryFactory;

    public List<RecommendItemQueryDto> getRecommendItemList(Member member) {
        BooleanExpression like = Expressions.asBoolean(false);

        JPAQuery<RecommendItemQueryDto> query = queryFactory
                .select(new QRecommendItemQueryDto(
                        recommend.id, item.id, item.name, item.brand, item.price, item.imgUrl, like, recommend.title, recommend.subTitle, recommend.linkParam)
                )
                .from(recommendItem)
                .leftJoin(recommendItem.item, item)
                .leftJoin(recommendItem.recommend, recommend);

        if (member != null) {
            query = query.leftJoin(recommendItem.item.itemLikes, itemLike)
                    .on(itemLike.member.eq(member).and(itemLike.item.eq(item)))
                    .where(itemLike.id.in(
                            JPAExpressions.select(itemLike.id)
                                    .from(itemLike)
                                    .where(itemLike.member.eq(member), itemLike.item.eq(item))
                                    .groupBy(itemLike.item.id)
                                    .having(itemLike.item.id.count().gt(1))
                    ));
            like = new CaseBuilder()
                    .when(itemLike.id.isNotNull())
                    .then((Predicate) Expressions.asBoolean(true))
                    .otherwise(Expressions.asBoolean(false));
        }

        return query.fetch();
    }

}
