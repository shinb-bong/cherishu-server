package cherish.backend.item.repository;

import cherish.backend.category.model.Category;
import cherish.backend.category.repository.CategoryRepository;
import cherish.backend.common.config.QueryDslConfig;
import cherish.backend.item.dto.ItemInfoResponseDto;
import cherish.backend.item.dto.QItemInfoResponseDto;
import cherish.backend.item.model.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static cherish.backend.item.model.QItemUrl.*;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom{
    private final QueryDslConfig queryDslConfig;
    private final ItemCategoryRepository itemCategoryRepository;
    private final ItemFilterRepository itemFilterRepository;
    private final CategoryRepository categoryRepository;
    private final ItemUrlRepository itemUrlRepository;

    @Override
    public ItemInfoResponseDto itemResponse(Long itemId) {
        // AliasCollisionException 방지
        QItem qItem = QItem.item;
        QItemUrl qItemUrl = itemUrl;
        QItemLike qItemLike = QItemLike.itemLike;
        QItem qItemLikeItem = new QItem("itemLikeItem");
        QItem qItemLikeUrl = new QItem("qItemLikeUrl");
        QItemFilter qItemFilter = QItemFilter.itemFilter;
        QItemCategory qItemCategory = QItemCategory.itemCategory;

        ItemInfoResponseDto content = queryDslConfig.jpaQueryFactory().select(new QItemInfoResponseDto(
                        qItem.id.as("itemId"), qItem.name.as("name"), qItem.brand.as("brand"), qItem.description.as("description"), qItem.price.as("price"),
                        qItem.imgUrl.as("imgUrl"), qItem.views.as("views"),
                        qItemLike.id.as("itemLikeId")))
                .from(qItem)
                .leftJoin(qItem.itemUrls, qItemUrl)
                .leftJoin(qItem.itemLikes, qItemLike)
                .leftJoin(qItemFilter).on(qItem.id.eq(qItemFilter.item.id))
                .leftJoin(qItemCategory).on(qItem.id.eq(qItemCategory.item.id))
                .leftJoin(qItemLike.item, qItemLikeItem)
                .leftJoin(qItemUrl.item, qItemLikeUrl)
                .where(itemIdEq(itemId))
                .fetchFirst();

        Map<String, String> itemUrls = itemUrls(itemId);
        content.setBrandUrl(itemUrls.get("brand"));
        content.setKakaoUrl(itemUrls.get("kakao"));
        content.setCoupangUrl(itemUrls.get("coupang"));
        content.setNaverUrl(itemUrls.get("naver"));

        content.setTagList(getTagList(itemId));

        return content;
    }

    private List<String> getTagList(Long itemId) {
        List<String> tagList = new ArrayList<>();

        List<Long> categoryIds = itemCategoryRepository.findCategoryIdByItem(itemId);
        Collections.shuffle(categoryIds);
        Long categoryId = categoryIds.stream().findFirst().orElse(null);
        Optional<Category> category = categoryRepository.findById(categoryId);

        List<String> itemFilters = itemFilterRepository.findItemFilterNameByFilterId(itemId, 5L); // preferenceId = 5L
        Collections.shuffle(itemFilters);
        List<String> categoryNameList = itemFilters.stream().limit(2).toList();
        tagList.add(0, category.get().getName());
        tagList.add(1, String.valueOf(categoryNameList));

        return tagList;
    }

    private BooleanExpression itemIdEq(Long itemId) {
        return hasText(String.valueOf(itemId)) ? QItem.item.id.eq(itemId) : null;
    }

//    private String itemUrlByPlatform(String platform, Long itemId) {
//        return itemUrlRepository.findByPlatform(platform, itemId);
//    }

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
            itemUrls.put(platform, url);
        }

        return itemUrls;
    }
}