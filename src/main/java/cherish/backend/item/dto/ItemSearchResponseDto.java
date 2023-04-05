package cherish.backend.item.dto;

import lombok.*;

import java.util.List;

public class ItemSearchResponseDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
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
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FilterDto {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
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
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryDto {
        private Long id;
        private String name;
        private CategoryDto parent;
        private List<String> children;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class JobDto {
        private Long id;
        private String name;
        private JobDto parent;
        private List<String> children;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemUrlDto {
        private Long id;
        private String url;
        private String platform;
    }

}
