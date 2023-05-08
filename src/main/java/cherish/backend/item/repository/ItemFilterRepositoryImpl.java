package cherish.backend.item.repository;

import cherish.backend.common.config.QueryDslConfig;
import cherish.backend.item.constant.ItemSortConstants;
import cherish.backend.item.dto.*;
import cherish.backend.member.model.Member;
import cherish.backend.member.model.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static cherish.backend.category.model.QCategory.category;
import static cherish.backend.category.model.QFilter.filter;
import static cherish.backend.item.model.QItem.item;
import static cherish.backend.item.model.QItemCategory.itemCategory;
import static cherish.backend.item.model.QItemFilter.itemFilter;
import static cherish.backend.item.model.QItemJob.itemJob;
import static cherish.backend.item.model.QItemLike.itemLike;
import static cherish.backend.member.model.QJob.job;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.util.StringUtils.hasText;

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
    public Page<ItemSearchResponseDto> searchItem(ItemSearchCondition searchCondition, Member member, Pageable pageable) {
        BooleanExpression isLiked = member != null ? new CaseBuilder().when(itemLike.member.eq(member)).then(true).otherwise(false) : Expressions.asBoolean(false);

        List<ItemSearchResponseDto> content = queryDslConfig.jpaQueryFactory()
                .selectDistinct(Projections.constructor(ItemSearchResponseDto.class,
                        item.id, item.name, item.brand, item.description, item.price, item.imgUrl, isLiked.as("isLiked"), item.views, item.modifiedDate))
                .from(item)
                .leftJoin(item.itemFilters, itemFilter)
                .leftJoin(item.itemJobs, itemJob)
                .leftJoin(item.itemCategories, itemCategory)
                .leftJoin(itemFilter.filter, filter)
                .leftJoin(itemJob.job, job)
                .leftJoin(itemCategory.category, category)
                .leftJoin(itemLike).on(item.id.eq(itemLike.item.id).and(member != null ? itemLike.member.id.eq(member.getId()) : null))
                .leftJoin(itemLike.member, QMember.member)
                .where(getSearchCondition(searchCondition))
                .orderBy(getOrderSpecifier(searchCondition.getSort())) // 기본 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = queryDslConfig.jpaQueryFactory()
                .select(item.id.count())
                .from(item)
                .leftJoin(item.itemFilters, itemFilter)
                .leftJoin(item.itemJobs, itemJob)
                .leftJoin(item.itemCategories, itemCategory)
                .leftJoin(itemFilter.filter, filter)
                .leftJoin(itemJob.job, job)
                .leftJoin(itemCategory.category, category)
                .leftJoin(itemLike).on(item.id.eq(itemLike.item.id).and(member != null ? itemLike.member.id.eq(member.getId()) : null))
                .leftJoin(itemLike.member, QMember.member)
                .where(getSearchCondition(searchCondition))
                .distinct();

        return PageableExecutionUtils.getPage(content, pageable, total::fetchFirst);
    }

    private OrderSpecifier<?> getOrderSpecifier(final String sort) {
        if (StringUtils.isEmpty(sort)) {
            return new OrderSpecifier<>(Order.ASC, item.id);
        }
        return switch (sort) {
            case ItemSortConstants.MOST_RECOMMENDED -> new OrderSpecifier<>(Order.ASC, item.id);
            case ItemSortConstants.MOST_POPULAR -> new OrderSpecifier<>(Order.DESC, item.views);
            case ItemSortConstants.LATEST -> new OrderSpecifier<>(Order.DESC, item.modifiedDate);
            case ItemSortConstants.MOST_EXPENSIVE -> new OrderSpecifier<>(Order.DESC, item.price);
            case ItemSortConstants.LEAST_EXPENSIVE -> new OrderSpecifier<>(Order.ASC, item.price);
            default -> throw new IllegalArgumentException("Sort error");
        };
    }

    private BooleanBuilder getSearchCondition(ItemSearchCondition searchCondition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (isNotEmpty(searchCondition.getKeyword())) {
            String keyword = searchCondition.getKeyword();
            booleanBuilder = booleanBuilder.or(
                    item.name.contains(keyword)
                            .or(item.brand.contains(keyword))
                            .or(category.name.contains(keyword))
                            .or(itemCategory.category.name.contains(keyword))
                            .or(job.name.contains(keyword))
                            .or(itemJob.job.name.contains(keyword))
                            .or(filter.name.contains(keyword))
                            .or(itemFilter.filter.name.contains(keyword))
            );
        }

        // 필터링 조건 추가
        if (searchCondition.getCategoryName() != null && !searchCondition.getCategoryName().isEmpty()) {
            BooleanExpression categoryExpression = null;
            for (String categoryName : searchCondition.getCategoryName()) {
                if (categoryExpression == null) {
                    categoryExpression = category.name.contains(categoryName);
                } else {
                    categoryExpression = categoryExpression.or(category.name.contains(categoryName));                }
            }
            booleanBuilder.and(categoryExpression);
        }

        if (isNotEmpty(searchCondition.getJobName())) {
            booleanBuilder.and(job.name.containsIgnoreCase(searchCondition.getJobName()));
        }

        if (!isNotEmpty(searchCondition.getGender()) &&isNotEmpty(searchCondition.getSituationName())) {
            booleanBuilder.and(itemFilter.name.containsIgnoreCase(searchCondition.getSituationName()));
        }

        if (isNotEmpty(searchCondition.getGender()) && !isNotEmpty(searchCondition.getSituationName())) {
            booleanBuilder.and(itemFilter.name.containsIgnoreCase(searchCondition.getGender()));
        }

        if (isNotEmpty(searchCondition.getSituationName()) && isNotEmpty(searchCondition.getGender())) {
            List<Long> list = queryDslConfig.jpaQueryFactory().select(itemFilter.item.id)
                    .from(itemFilter)
                    .where(itemFilter.name.in(searchCondition.getSituationName(), searchCondition.getGender()))
                    .groupBy(itemFilter.item.id)
                    .having(itemFilter.name.countDistinct().goe(2))
                    .fetch();

            booleanBuilder.and(item.id.in(list));
        }

        return booleanBuilder;
    }

    private BooleanExpression itemFilterNameEq(String itemFilterName) {
        return hasText(itemFilterName) ? itemFilter.name.containsIgnoreCase(itemFilterName) : null;
    }

    private BooleanExpression filterIdEq(Long filterId) {
        return hasText(String.valueOf(filterId)) ? filter.id.eq(filterId) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? item.maxAge.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? item.minAge.loe(ageLoe) : null;
    }

}
