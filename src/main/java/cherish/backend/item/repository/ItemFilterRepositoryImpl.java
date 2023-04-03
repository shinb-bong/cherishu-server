package cherish.backend.item.repository;

import cherish.backend.item.dto.*;
import cherish.backend.item.model.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
import static cherish.backend.item.model.QItemUrl.*;
import static cherish.backend.member.model.QJob.job;
import static org.springframework.util.StringUtils.*;

public class ItemFilterRepositoryImpl implements ItemFilterRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ItemFilterRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ItemFilterQueryDto> findItemFilterByNameAndId(ItemFilterCondition filterCondition) {
        return queryFactory
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
        return queryFactory
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
    public Page<ItemSearchQueryDto> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {

        List<ItemSearchQueryDto> content = queryFactory.select(new QItemSearchQueryDto(
                        filter.id.as("filterId"),
                        itemFilter.id.as("itemFilterId"),
                        item.id.as("itemId"),
                        category.id.as("categoryId"),
                        itemCategory.id.as("itemCategoryId"),
                        job.id.as("jobId"),
                        itemJob.id.as("itemJobId"),
                        itemUrl.id.as("itemUrlId"),
                        filter.name.as("filterName"),
                        itemFilter.name.as("itemFilterName"),
                        category.parent.name.as("categoryParent"),
                        category.children.any().name.as("categoryChildren"),
                        job.parent.name.as("jobParent"),
                        job.children.any().name.as("jobChildren"),
                        item.name.as("itemName"),
                        item.brand.as("itemBrand")
                )).from(item)
                .leftJoin(item.itemFilters, itemFilter).fetchJoin()
                .leftJoin(item.itemJobs, itemJob).fetchJoin()
                .leftJoin(item.itemCategories, itemCategory).fetchJoin()
                .leftJoin(itemUrl).on(itemUrl.item.id.eq(item.id))
                .leftJoin(itemFilter.filter, filter)
                .leftJoin(itemJob.job, job)
                .leftJoin(itemCategory.category, category)
                .where(
                        itemFilterNameEq(searchCondition.getItemFilterName()),
                        filterNameEq(searchCondition.getFilterName()),
                        categoryParentEq(searchCondition.getItemCategoryParent()),
                        categoryChildrenEq(searchCondition.getItemCategoryChildren()),
                        jobParentEq(searchCondition.getItemJobParent()),
                        jobChildrenEq(searchCondition.getItemJobChildren()),
                        itemNameEq(searchCondition.getItemName()),
                        itemBrandEq(searchCondition.getItemBrand()),
                        keywordEq(searchCondition.getKeyword(), searchCondition.getTarget())
                )
                .fetch();

        JPAQuery<Item> countQuery = queryFactory.selectFrom(item)
                .leftJoin(item.itemFilters, itemFilter)
                .leftJoin(item.itemJobs, itemJob)
                .leftJoin(item.itemCategories, itemCategory)
                .leftJoin(itemUrl).on(itemUrl.item.id.eq(item.id))
                .leftJoin(itemFilter.filter, filter)
                .leftJoin(itemJob.job, job)
                .leftJoin(itemCategory.category, category)
                .where(
                        itemFilterNameEq(searchCondition.getItemFilterName()),
                        filterNameEq(searchCondition.getFilterName()),
                        categoryParentEq(searchCondition.getItemCategoryParent()),
                        categoryChildrenEq(searchCondition.getItemCategoryChildren()),
                        jobParentEq(searchCondition.getItemJobParent()),
                        jobChildrenEq(searchCondition.getItemJobChildren()),
                        itemNameEq(searchCondition.getItemName()),
                        itemBrandEq(searchCondition.getItemBrand()),
                        keywordEq(searchCondition.getKeyword(), searchCondition.getTarget())
                );

        long total = countQuery.fetchCount();
        return new PageImpl<>(content, pageable, total);
    }


    private BooleanExpression categoryParentEq(String categoryParent) {
        return StringUtils.hasText(categoryParent) ? category.parent.name.eq(categoryParent) : null;
    }

    private BooleanExpression categoryChildrenEq(String categoryChildren) {
        return StringUtils.hasText(categoryChildren) ? category.children.any().name.eq(categoryChildren) : null;
    }

    private BooleanExpression jobParentEq(String jobParent) {
        return StringUtils.hasText(jobParent) ? job.parent.name.eq(jobParent) : null;
    }

    private BooleanExpression jobChildrenEq(String jobChildren) {
        return StringUtils.hasText(jobChildren) ? job.children.any().name.eq(jobChildren) : null;
    }

    private BooleanExpression itemNameEq(String itemName) {
        return StringUtils.hasText(itemName) ? item.name.eq(itemName) : null;
    }

    private BooleanExpression itemFilterNameEq(String itemFilterName) {
        return hasText(itemFilterName) ? itemFilter.name.eq(itemFilterName) : null;
    }

    private BooleanExpression itemBrandEq(String itemBrand) {
        return hasText(itemBrand) ? item.brand.eq(itemBrand) : null;
    }

    private BooleanExpression filterIdEq(Long filterId) {
        return hasText(String.valueOf(filterId)) ? filter.id.eq(filterId) : null;
    }

    private BooleanExpression filterNameEq(String filterName) {
        return hasText(String.valueOf(filterName)) ? filter.name.eq(filterName) : null;
    }
    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? item.maxAge.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? item.minAge.loe(ageLoe) : null;
    }

    private BooleanExpression keywordEq(String keyword, String target) {
        return keyword != null ? targetEqWithKeyword(keyword, target) : null;
    }

    private BooleanExpression targetEqWithKeyword(String keyword, String target) {
        if (target == null) {
            return null;
        }
        switch (target) {
            case "filterName":
                return filter.name.contains(keyword);
            case "itemFilterName":
                return itemFilter.name.contains(keyword);
            case "categoryParent":
                return category.parent.name.contains(keyword);
            case "categoryChildren":
                return category.children.any().name.contains(keyword);
            case "jobParent":
                return job.parent.name.contains(keyword);
            case "jobChildren":
                return job.children.any().name.contains(keyword);
            case "itemName":
                return item.name.contains(keyword);
            case "itemBrand":
                return item.brand.contains(keyword);
            default:
                throw new IllegalArgumentException("Invalid target: " + target);
        }
    }

}
