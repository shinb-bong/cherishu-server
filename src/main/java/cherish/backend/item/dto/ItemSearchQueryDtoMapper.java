package cherish.backend.item.dto;

import cherish.backend.item.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
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
    ItemSearchResponseDto.ResponseSearchItem toResponseSearchItem(ItemSearchQueryDto itemSearchQueryDto);

    default ItemSearchResponseDto.FilterDto toFilterDto(Long filterId, String filterName) {
        return ItemSearchResponseDto.FilterDto.builder()
                .id(filterId)
                .name(filterName)
                .build();
    }

    default ItemFilter mapItemFilter(Long itemFilterId, String itemFilterName) {
        return ItemFilter.builder()
                .id(itemFilterId)
                .name(itemFilterName)
                .build();
    }

    default ItemSearchResponseDto.ItemDto toItemDto(Long id, int price, int minAge, int maxAge) {
        return ItemSearchResponseDto.ItemDto.builder()
                .id(id)
                .price(price)
                .minAge(minAge)
                .maxAge(maxAge)
                .build();
    }
    default ItemSearchResponseDto.CategoryDto mapCategory(Long categoryId, String categoryParent, String categoryChildren) {
        List<String> children = new ArrayList<>();
        if (categoryChildren != null) {
            for (String child : categoryChildren.split(",")) {
                children.add(child);
            }
        }
        return ItemSearchResponseDto.CategoryDto.builder()
                .id(categoryId)
                .parent(ItemSearchResponseDto.CategoryDto.builder().name(categoryParent).build())
                .children(children)
                .build();
    }


    default ItemSearchResponseDto.JobDto toJobDto(Long jobId, String jobParent, String jobChildren) {
        List<String> children = new ArrayList<>();
        if (jobChildren != null) {
            for (String child : jobChildren.split(",")) {
                children.add(child);
            }
        }
        return ItemSearchResponseDto.JobDto.builder()
                .id(jobId)
                .parent(ItemSearchResponseDto.JobDto.builder().name(jobParent).build())
                .children(children)
                .build();
    }

    default ItemSearchResponseDto.ItemUrlDto toItemUrlDto(Long id, String url, String platform) {
        return ItemSearchResponseDto.ItemUrlDto.builder()
                .id(id)
                .url(url)
                .platform(platform)
                .build();
    }

}