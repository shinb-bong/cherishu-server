package cherish.backend.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

public class ItemSearchResponseDto {
    @Data
    @NoArgsConstructor
    @Builder
    public static class ResponseSearchItem {
        private FilterDto filter;
        private FilterDto itemFilter;
        private ItemDto item;
        private CategoryDto category;
        private CategoryDto itemCategory;
        private JobDto job;
        private JobDto itemJob;
        private ItemUrlDto itemUrl;
        @QueryProjection
        public ResponseSearchItem(FilterDto filter, FilterDto itemFilter, ItemDto item, CategoryDto category, CategoryDto itemCategory, JobDto job, JobDto itemJob, ItemUrlDto itemUrl) {
            this.filter = filter;
            this.itemFilter = itemFilter;
            this.item = item;
            this.category = category;
            this.itemCategory = itemCategory;
            this.job =job;
            this.itemJob = itemJob;
            this.itemUrl = itemUrl;
        }
    }

    @Data
    @NoArgsConstructor
    @Builder
    public static class FilterDto {
        private Long id;
        private String name;
        @QueryProjection
        public FilterDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Data
    @NoArgsConstructor
    @Builder
    public static class ItemDto {
        private Long id;
        private String name;
        private String brand;
        private String description;
        private int price;
        private String imgUrl;
        private int minAge;
        private int maxAge;
        @QueryProjection
        public ItemDto(Long id, String name, String brand, String description, int price, String imgUrl, int minAge, int maxAge) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.description = description;
            this.price = price;
            this.imgUrl = imgUrl;
            this.minAge = minAge;
            this.maxAge = maxAge;
        }
    }

    @Data
    @NoArgsConstructor
    @Builder
    public static class CategoryDto {
        private Long id;
        private String name;
        private CategoryDto parent;
        private List<String> children;
        @QueryProjection
        public CategoryDto(Long id, String name, CategoryDto parent, List<String> children) {
            this.id = id;
            this.name = name;
            this.parent = parent;
            this.children = children;
        }
    }

    @Data
    @NoArgsConstructor
    @Builder
    public static class JobDto {
        private Long id;
        private String name;
        private JobDto parent;
        private List<String> children;
        @QueryProjection
        public JobDto(Long id, String name, JobDto parent, List<String> children) {
            this.id = id;
            this.name = name;
            this.parent = parent;
            this.children = children;
        }
    }

    @Data
    @NoArgsConstructor
    @Builder
    public static class ItemUrlDto {
        private Long id;
        private String url;
        private String platform;
        @QueryProjection
        public ItemUrlDto(Long id, String url, String platform) {
            this.id = id;
            this.url = url;
            this.platform = platform;
        }
    }

}