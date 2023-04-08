package cherish.backend.item.repository;

import cherish.backend.common.config.QueryDslConfig;
import cherish.backend.item.dto.*;
import cherish.backend.item.model.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static cherish.backend.category.model.QCategory.*;
import static cherish.backend.category.model.QFilter.*;
import static cherish.backend.item.model.QItem.*;
import static cherish.backend.item.model.QItemCategory.*;
import static cherish.backend.item.model.QItemFilter.*;
import static cherish.backend.item.model.QItemJob.itemJob;
import static cherish.backend.member.model.QJob.job;
import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
public class ItemFilterRepositoryImpl implements ItemFilterRepositoryCustom{

    private final QueryDslConfig queryDslConfig;

    @Override
    public List<ItemFilterQueryDto> findItemFilterByNameAndId(ItemFilterCondition filterCondition) {
        return queryDslConfig.jpaQueryFactory()
                .select(new QItemFilterQueryDto(
                        item.id.as("itemId"),
                        filter.id.as("filterId"),
                        itemFilter.id.as("itemFilterId"),
                        item.name.as("itemName"),
                        filter.name.as("filterName"),
                        itemFilter.name.as("itemFilterName"),
                        item.price.as("itemPrice")
                ))
                .from(itemFilter)
                .join(itemFilter.filter, filter)
                .where(
                        itemFilterNameEq(filterCondition.getItemFilterName()),
                        filterIdEq(filterCondition.getFilterId()))
                .fetch();
    }

    @Override
    public List<AgeFilterQueryDto> findItemFilterByAge(AgeFilterCondition ageCondition) {
        return queryDslConfig.jpaQueryFactory()
                .select(new QAgeFilterQueryDto(
                        item.id.as("itemId"),
                        filter.id.as("filterId"),
                        itemFilter.id.as("itemFilterId"),
                        item.name.as("itemName"),
                        filter.name.as("filterName"),
                        itemFilter.name.as("itemFilterName"),
                        item.minAge.as("minAge"),
                        item.maxAge.as("maxAge")
                ))
                .from(itemFilter)
                .join(itemFilter.filter, filter)
                .where(
                        itemFilterNameEq(ageCondition.getItemFilterName()),
                        filterIdEq(ageCondition.getFilterId()),
                        ageGoe(ageCondition.getAgeGoe()),
                        ageLoe(ageCondition.getAgeLoe()))
                .fetch();
    }

    @Override
    public Page<ItemSearchResponseDto.ResponseSearchItem> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {
        List<ItemSearchResponseDto.ResponseSearchItem> content = queryDslConfig.jpaQueryFactory()
                .selectDistinct(new QItemSearchResponseDto_ResponseSearchItem(
                        new QItemSearchResponseDto_ItemDto(item.id, item.name, item.brand, item.description, item.price, item.imgUrl)
                ))
                .from(item)
                .leftJoin(item.itemFilters, itemFilter)
                .leftJoin(item.itemJobs, itemJob)
                .leftJoin(item.itemCategories, itemCategory)
                .leftJoin(itemFilter.filter, filter)
                .leftJoin(itemJob.job, job)
                .leftJoin(itemCategory.category, category)
                .where(getSearchCondition(searchCondition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Item> countQuery = queryDslConfig.jpaQueryFactory()
                .selectFrom(item)
                .leftJoin(item.itemFilters, itemFilter)
                .leftJoin(item.itemJobs, itemJob)
                .leftJoin(item.itemCategories, itemCategory)
                .leftJoin(itemFilter.filter, filter)
                .leftJoin(itemJob.job, job)
                .leftJoin(itemCategory.category, category)
                .where(getSearchCondition(searchCondition))
                .distinct()
                .select(item);

        long total = countQuery.fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate getSearchCondition(ItemSearchCondition searchCondition) {
        if (searchCondition.getKeyword() == null) { return null; }
        return itemNameEq(searchCondition.getKeyword())
                .or(itemBrandEq(searchCondition.getKeyword()))
                .or(itemFilterNameEq(searchCondition.getKeyword()))
                .or(filterNameEq(searchCondition.getKeyword()))
                .or(categoryNameEq(searchCondition.getKeyword()))
                .or(jobNameEq(searchCondition.getKeyword()));
    }

    private BooleanExpression categoryNameEq(String keyword) {
        return category.name.containsIgnoreCase(keyword)
                .or(category.children.any().name.containsIgnoreCase(keyword))
                .or(itemCategory.category.name.containsIgnoreCase(keyword))
                .or(itemCategory.category.children.any().name.containsIgnoreCase(keyword));
    }

    private BooleanExpression jobNameEq(String keyword) {
        return job.name.containsIgnoreCase(keyword)
                .or(job.children.any().name.containsIgnoreCase(keyword))
                .or(itemJob.job.name.containsIgnoreCase(keyword))
                .or(itemJob.job.children.any().name.containsIgnoreCase(keyword));
    }

    private BooleanExpression itemNameEq(String itemName) {
        return StringUtils.hasText(itemName) ? item.name.eq(itemName) : Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression itemFilterNameEq(String itemFilterName) {
        return hasText(itemFilterName) ? itemFilter.name.eq(itemFilterName) : Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression itemBrandEq(String itemBrand) {
        return hasText(itemBrand) ? item.brand.eq(itemBrand) : Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression filterIdEq(Long filterId) {
        return hasText(String.valueOf(filterId)) ? filter.id.eq(filterId) : null;
    }

    private BooleanExpression filterNameEq(String filterName) {
        return hasText(filterName) ? filter.name.eq(filterName) : Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? item.maxAge.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? item.minAge.loe(ageLoe) : null;
    }

}
