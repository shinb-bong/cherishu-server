package cherish.backend.item.dto;

public class ItemSearchQueryDtoMapper {
    public ItemSearchDto.ResponseSearchItem map(ItemSearchQueryDto searchQueryDto) {
        ItemSearchDto.ResponseSearchItem.ResponseSearchItemBuilder builder = ItemSearchDto.ResponseSearchItem.builder();

        // FilterDto 매핑
        if (searchQueryDto.getFilterId() != null) {
            builder.filter(ItemSearchDto.FilterDto.builder()
                    .id(searchQueryDto.getFilterId())
                    .name(searchQueryDto.getFilterName())
                    .build());
        }

        if (searchQueryDto.getItemFilterId() != null) {
            builder.itemFilter(ItemSearchDto.FilterDto.builder()
                    .id(searchQueryDto.getItemFilterId())
                    .name(searchQueryDto.getItemFilterName())
                    .build());
        }

        // ItemDto 매핑
        if (searchQueryDto.getItemId() != null) {
            builder.item(ItemSearchDto.ItemDto.builder()
                    .id(searchQueryDto.getItemId())
                    .name(searchQueryDto.getItemName())
                    .brand(searchQueryDto.getItemBrand())
                    .description("")
                    .price(0)
                    .imgUrl("")
                    .build());
        }

        // CategoryDto 매핑
        if (searchQueryDto.getCategoryId() != null) {
            builder.category(ItemSearchDto.CategoryDto.builder()
                    .id(searchQueryDto.getCategoryId())
                    .name(searchQueryDto.getCategoryParent())
                    .build());
        }

        if (searchQueryDto.getItemCategoryId() != null) {
            builder.itemCategory(ItemSearchDto.CategoryDto.builder()
                    .id(searchQueryDto.getItemCategoryId())
                    .name(searchQueryDto.getCategoryChildren())
                    .build());
        }

        // JobDto 매핑
        if (searchQueryDto.getJobId() != null) {
            builder.job(ItemSearchDto.JobDto.builder()
                    .id(searchQueryDto.getJobId())
                    .name(searchQueryDto.getJobParent())
                    .build());
        }

        if (searchQueryDto.getItemJobId() != null) {
            builder.itemJob(ItemSearchDto.JobDto.builder()
                    .id(searchQueryDto.getItemJobId())
                    .name(searchQueryDto.getJobChildren())
                    .build());
        }

        // ItemUrlDto 매핑
        if (searchQueryDto.getItemUrlId() != null) {
            builder.itemUrl(ItemSearchDto.ItemUrlDto.builder()
                    .id(searchQueryDto.getItemUrlId())
                    .url("")
                    .platform("")
                    .build());
        }

        return builder.build();
    }
}