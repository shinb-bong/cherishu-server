package cherish.backend.item.repository;

import cherish.backend.category.model.QCategory;
import cherish.backend.common.config.QueryDslConfig;
import cherish.backend.item.dto.ItemInfoResponseDto;
import cherish.backend.item.model.*;
import cherish.backend.member.model.Member;
import cherish.backend.member.model.QMember;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom{
    private final QueryDslConfig queryDslConfig;

    @Override
    public List<ItemInfoResponseDto> itemResponse(Long itemId, Member member) {
        // AliasCollisionException 방지
        QItem qItem = QItem.item;
        QItemFilter qItemFilter = QItemFilter.itemFilter;
        QItemCategory qItemCategory = QItemCategory.itemCategory;
        QCategory qCategory = QCategory.category;
        QItemUrl qItemUrl = QItemUrl.itemUrl;
        QItemLike qItemLike = QItemLike.itemLike;
        QMember qMember = QMember.member;

        BooleanExpression isLiked = member != null ? new CaseBuilder().when(qItemLike.member.eq(member)).then(true).otherwise(false) : Expressions.asBoolean(false);

        List<ItemInfoResponseDto> content = queryDslConfig.jpaQueryFactory()
                .select(Projections.constructor(ItemInfoResponseDto.class,
                        qItem.id.as("itemId"), qItem.name.as("name"), qItem.brand.as("brand"),
                        qItem.description.as("description"), qItem.price.as("price"),
                        qItem.imgUrl.as("imgUrl"), qItem.views.as("views"),
                        qItemUrl.platform.as("platform"), qItemUrl.url.as("url"),
                        qItemFilter.name.as("filterTag"), qCategory.name.as("categoryTag"), isLiked.as("isLiked"), qMember.id.as("memberId")))
                .from(qItem)
                .innerJoin(qItemUrl).on(qItem.id.eq(qItemUrl.item.id))
                .innerJoin(qItemCategory).on(qItem.id.eq(qItemCategory.item.id))
                .innerJoin(qCategory).on(qItemCategory.category.id.eq(qCategory.id))
                .innerJoin(qItemFilter).on(qItem.id.eq(qItemFilter.item.id))
                .leftJoin(qItemLike).on(qItem.id.eq(qItemLike.item.id)
                        .and(member != null ? qItemLike.member.id.eq(member.getId()) : null))
                .leftJoin(qItemLike.member, qMember)
                .where(qItem.id.eq(itemId).and(qItemFilter.filter.id.eq(5L))
                        .and(qItem.id.stringValue().substring(0, 1).eq(qCategory.id.stringValue())))
                .fetch();

        return content;
    }
}