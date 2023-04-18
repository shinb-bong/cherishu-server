package cherish.backend.item.repository;

import cherish.backend.category.model.Category;
import cherish.backend.category.model.QCategory;
import cherish.backend.category.model.QFilter;
import cherish.backend.common.config.QueryDslConfig;
import cherish.backend.item.dto.ItemInfoResponseDto;
import cherish.backend.item.model.*;
import cherish.backend.member.model.Member;
import cherish.backend.member.model.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static cherish.backend.item.model.QItemUrl.*;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom{
    private final QueryDslConfig queryDslConfig;

    // AliasCollisionException 방지
    QItem qItem = QItem.item;
    QItemUrl qItemUrl = itemUrl;
    QItemLike qItemLike = QItemLike.itemLike;
    QItemFilter qItemFilter = QItemFilter.itemFilter;
    QItemCategory qItemCategory = QItemCategory.itemCategory;

    @Override
    public ItemInfoResponseDto itemResponse(Long itemId, Member member) {
        BooleanExpression isLiked = new CaseBuilder().when(qItemLike.id.isNotNull()).then(true).otherwise(false);

        ItemInfoResponseDto content = queryDslConfig.jpaQueryFactory()
                .select(Projections.constructor(ItemInfoResponseDto.class,
                        qItem.id.as("itemId"), qItem.name.as("name"), qItem.brand.as("brand"),
                        qItem.description.as("description"), qItem.price.as("price"),
                        qItem.imgUrl.as("imgUrl"), qItem.views.as("views"),
                        isLiked.as("isLiked"), QMember.member.id))
                .from(qItem)
                .leftJoin(qItemFilter).on(qItem.id.eq(qItemFilter.item.id))
                .leftJoin(qItemLike).on(qItem.id.eq(qItemLike.item.id))
                .leftJoin(QMember.member).on(qItemLike.member.id.eq(QMember.member.id))
                .where(itemIdEq(itemId),
                        memberIdEq(member))
                .fetchFirst();

        content.setTagList(getTagList(itemId));
        content.setUrl(itemUrls(itemId));

        return content;
    }

    private List<String> getTagList(Long itemId) {
        List<String> tagList = new ArrayList<>();

        Category category = queryDslConfig.jpaQueryFactory()
                .selectFrom(QCategory.category)
                .where(QCategory.category.id.in(
                        JPAExpressions.select(qItemCategory.category.id)
                                .from(qItemCategory)
                                .where(qItemCategory.item.id.eq(itemId))
                ))
                .fetchFirst();

        List<String> categoryNameList = queryDslConfig.jpaQueryFactory()
                .select(qItemFilter.name)
                .from(qItemFilter)
                .join(qItemFilter.item.itemCategories, qItemCategory)
                .where(qItemCategory.item.id.eq(itemId).and(QFilter.filter.id.eq(5L)))
                .distinct()
                .limit(2)
                .fetch();

        tagList.add(0, category.getName());
        tagList.add(1, String.valueOf(categoryNameList));

        return tagList;
    }

    private Map<String, String> itemUrls(Long itemId) {
        Map<String, String> itemUrls = new HashMap<>();

        List<Tuple> results = queryDslConfig.jpaQueryFactory()
                .select(itemUrl.platform, itemUrl.url)
                .from(itemUrl)
                .where(itemUrl.item.id.eq(itemId))
                .fetch();

        for (Tuple result : results) {
            String platform = result.get(itemUrl.platform);
            String url = result.get(itemUrl.url);
            if (platform != null) {
                itemUrls.put(platform, url != null ? url : "");
            }
        }

        return itemUrls;
    }

    private BooleanExpression itemIdEq(Long itemId) {
        return hasText(String.valueOf(itemId)) ? QItem.item.id.eq(itemId) : null;
    }

    private BooleanExpression memberIdEq(Member member) {
        return member != null ? QMember.member.id.eq(member.getId()) : null;
    }
}