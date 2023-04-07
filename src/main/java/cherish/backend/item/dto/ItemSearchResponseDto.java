package cherish.backend.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

public class ItemSearchResponseDto {
    @Data
    @NoArgsConstructor
    @Builder
    public static class ResponseSearchItem {
        private ItemDto item;

        @QueryProjection
        public ResponseSearchItem(ItemDto item) {
            this.item = item;
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

        @QueryProjection
        public ItemDto(Long id, String name, String brand, String description, int price, String imgUrl) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.description = description;
            this.price = price;
            this.imgUrl = imgUrl;
        }
    }
}
