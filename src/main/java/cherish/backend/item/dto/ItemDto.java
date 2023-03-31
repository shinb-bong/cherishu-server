package cherish.backend.item.dto;

import lombok.*;

public class ItemDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestSearchItem {
        private Long id;
        private String brand;
        private String name;
        private String description;
        private int price;
        private int views;
        private String imgUrl;

        @Builder
        public RequestSearchItem(Long id, String brand, String name, String description, int price, int views, String imgUrl) {
            this.id = id;
            this.brand = brand;
            this.name = name;
            this.description = description;
            this.price = price;
            this.views = views;
            this.imgUrl = imgUrl;
        }

        @Override
        public String toString() {
            return "ItemDto{" +
                    "id=" + id +
                    ", brand='" + brand + '\'' +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", price=" + price +
                    ", views=" + views +
                    ", imgUrl='" + imgUrl + '\'' +
                    '}';
        }
    }

}
