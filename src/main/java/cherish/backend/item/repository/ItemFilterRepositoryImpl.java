package cherish.backend.item.repository;

import cherish.backend.common.config.QueryDslConfig;
import cherish.backend.item.dto.*;
import cherish.backend.item.model.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static cherish.backend.category.model.QCategory.*;
import static cherish.backend.category.model.QFilter.*;
import static cherish.backend.item.model.QItem.*;
import static cherish.backend.item.model.QItemCategory.*;
import static cherish.backend.item.model.QItemFilter.*;
import static cherish.backend.item.model.QItemJob.itemJob;
import static cherish.backend.member.model.QJob.job;
import static org.apache.commons.lang3.StringUtils.*;
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
    public Page<ItemSearchResponseDto> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {
        List<ItemSearchResponseDto> content = queryDslConfig.jpaQueryFactory()
                .selectDistinct(new QItemSearchResponseDto(
                        item.id, item.name, item.brand, item.description, item.price, item.imgUrl))
                .from(item)
                .leftJoin(item.itemFilters, itemFilter)
                .leftJoin(item.itemJobs, itemJob)
                .leftJoin(item.itemCategories, itemCategory)
                .leftJoin(itemFilter.filter, filter)
                .leftJoin(itemJob.job, job)
                .leftJoin(itemCategory.category, category)
                .where(getSearchCondition(searchCondition))
                .orderBy(item.id.asc()) // 기본 정렬
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

    private BooleanBuilder getSearchCondition(ItemSearchCondition searchCondition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (isNotEmpty(searchCondition.getKeyword())) {
            String keyword = searchCondition.getKeyword();
            booleanBuilder = booleanBuilder.or(
                    item.name.contains(keyword)
                            .or(item.brand.contains(keyword))
                            .or(category.name.contains(keyword))
                            .or(category.children.any().name.contains(keyword))
                            .or(itemCategory.category.name.contains(keyword))
                            .or(itemCategory.category.children.any().name.contains(keyword))
                            .or(job.name.contains(keyword))
                            .or(job.children.any().name.contains(keyword))
                            .or(itemJob.job.name.contains(keyword))
                            .or(itemJob.job.children.any().name.contains(keyword))
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

        if (isNotEmpty(searchCondition.getSituationName())) {
            booleanBuilder.and(itemFilter.name.containsIgnoreCase(searchCondition.getSituationName()));
        }

        if (isNotEmpty(searchCondition.getGender())) {
            booleanBuilder.and(itemFilter.name.containsIgnoreCase(searchCondition.getGender()));
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
