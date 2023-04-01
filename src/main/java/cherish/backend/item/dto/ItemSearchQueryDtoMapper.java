package cherish.backend.item.dto;

import cherish.backend.category.model.Category;
import cherish.backend.category.model.Filter;
import cherish.backend.item.model.*;
import cherish.backend.member.model.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemSearchQueryDtoMapper {

    @Mappings({
            @Mapping(source = "filter.id", target = "filterId"),
            @Mapping(source = "itemFilter.id", target = "itemFilterId"),
            @Mapping(source = "item.id", target = "itemId"),
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "itemCategory.id", target = "itemCategoryId"),
            @Mapping(source = "job.id", target = "jobId"),
            @Mapping(source = "itemJob.id", target = "itemJobId"),
            @Mapping(source = "itemUrl.id", target = "itemUrlId"),
            @Mapping(source = "filter.name", target = "filterName"),
            @Mapping(source = "itemFilter.name", target = "itemFilterName"),
            @Mapping(source = "category.parent", target = "categoryParent"),
            @Mapping(source = "category.children", target = "categoryChildren"),
            @Mapping(source = "job.parent", target = "jobParent"),
            @Mapping(source = "job.children", target = "jobChildren"),
            @Mapping(source = "item.name", target = "itemName"),
            @Mapping(source = "item.brand", target = "itemBrand"),
            @Mapping(source = "item.description", target = "description"),
            @Mapping(source = "item.price", target = "price"),
            @Mapping(source = "item.imgUrl", target = "imgUrl"),
            @Mapping(source = "itemUrl.url", target = "url"),
            @Mapping(source = "itemUrl.platform", target = "platform"),
    })
    ItemDto.ResponseSearchItem map(ItemSearchQueryDto itemSearchQueryDto);

    default Filter mapFilter(Long filterId, String filterName) {
        Filter filter = Filter.builder().id(filterId).name(filterName).build();
        return filter;
    }

    default ItemFilter mapItemFilter(Long itemFilterId, String itemFilterName) {
        ItemFilter itemFilter = ItemFilter.builder().id(itemFilterId).name(itemFilterName).build();
        return itemFilter;
    }

    default Item mapItem(Long itemId, String itemName, String itemBrand, String description, int price, String imgUrl) {
        Item item = Item.builder().id(itemId).name(itemName).brand(itemBrand).description(description).price(price).imgUrl(imgUrl).build();
        return item;
    }

    default Job mapJob(Long jobId, String jobParent, String jobChildren) {
        Job job = Job.builder()
                .id(jobId)
                .parent(Job.builder()
                        .name(jobParent)
                        .build())
                .children(Collections.singletonList(Job.builder()
                        .name(jobChildren)
                        .build()))
                .build();
        return job;
    }

    default ItemJob mapItemJob(Long itemJobId) {
        ItemJob itemJob = ItemJob.builder().id(itemJobId).build();
        return itemJob;
    }

    default Category mapCategory(Long categoryId, String categoryParent, String categoryChildren) {
        Category category = Category.builder().id(categoryId)
                .parent(Category.builder().name(categoryParent).build())
                .children(Collections.singletonList(Category.builder().name(categoryChildren).build()))
                .build();
        return category;
    }

    default ItemCategory mapItemCategory(Long itemCategoryid) {
        ItemCategory itemCategory = ItemCategory.builder().id(itemCategoryid).build();
        return itemCategory;
    }

    default ItemUrl mapItemUrl(Long itemUrlId, String platform) {
        ItemUrl itemUrl = ItemUrl.builder().id(itemUrlId).platform(platform).build();
        return itemUrl;
    }

}