package cherish.backend.item.dto;

import cherish.backend.item.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemSearchQueryDtoMapper {

    @Mappings({
            @Mapping(source = "filterId", target = "filter.id"),
            @Mapping(source = "filterName", target = "filter.name"),
            @Mapping(source = "itemFilterId", target = "itemFilter.id"),
            @Mapping(source = "itemFilterName", target = "itemFilter.name"),
            @Mapping(source = "itemId", target = "item.id"),
            @Mapping(source = "itemName", target = "item.name"),
            @Mapping(source = "itemBrand", target = "item.brand"),
            @Mapping(source = "categoryId", target = "category.id"),
            @Mapping(source = "categoryParent", target = "category.parent.name"),
            @Mapping(source = "categoryChildren", target = "category.children"),
            @Mapping(source = "itemCategoryId", target = "itemCategory.id"),
            @Mapping(source = "jobId", target = "job.id"),
            @Mapping(source = "jobParent", target = "job.parent.name"),
            @Mapping(source = "jobChildren", target = "job.children"),
            @Mapping(source = "itemJobId", target = "itemJob.id"),
            @Mapping(source = "itemUrlId", target = "itemUrl.id")
    })
    ItemSearchDto.ResponseSearchItem toResponseSearchItem(ItemSearchQueryDto itemSearchQueryDto);

    default ItemSearchDto.FilterDto toFilterDto(Long filterId, String filterName) {
        return ItemSearchDto.FilterDto.builder()
                .id(filterId)
                .name(filterName)
                .build();
    }


    default ItemFilter mapItemFilter(Long itemFilterId, String itemFilterName) {
        ItemFilter itemFilter = ItemFilter.builder().id(itemFilterId).name(itemFilterName).build();
        return itemFilter;
    }

    default ItemSearchDto.ItemDto toItemDto(Long id, String name, String brand) {
        return ItemSearchDto.ItemDto.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .build();
    }

    default ItemSearchDto.CategoryDto mapCategory(Long categoryId, String categoryParent, String categoryChildren) {
        ItemSearchDto.CategoryDto category = ItemSearchDto.CategoryDto.builder()
                .id(categoryId)
                .parent(ItemSearchDto.CategoryDto.builder().name(categoryParent).build())
                .children(mapCategoryChildren(categoryChildren)) // categoryChildren 매핑
                .build();
        return category;
    }

    default ItemSearchDto.JobDto toJobDto(Long id, String name, ItemSearchDto.JobDto parent, String jobChildren) {
        return ItemSearchDto.JobDto.builder()
                .id(id)
                .name(name)
                .parent(parent)
                .children(jobChildren != null ? mapJobChildren(jobChildren) : Collections.emptyList())
                .build();
    }

    default ItemSearchDto.ItemUrlDto toItemUrlDto(Long id, String url, String platform) {
        return ItemSearchDto.ItemUrlDto.builder()
                .id(id)
                .url(url)
                .platform(platform)
                .build();
    }

    default List<ItemSearchDto.CategoryDto> mapCategoryChildren(String categoryChildren) {
        return Collections.singletonList(ItemSearchDto.CategoryDto.builder().name(categoryChildren).build());
    }

    default List<ItemSearchDto.JobDto> mapJobChildren(String jobChildren) {
        return Collections.singletonList(ItemSearchDto.JobDto.builder().name(jobChildren).build());
    }

}