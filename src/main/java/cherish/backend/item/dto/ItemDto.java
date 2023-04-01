package cherish.backend.item.dto;

import lombok.*;

public class ItemDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ResponseSearchItem {
        private Long id;
        private String brand;
        private String name;
        private String description;
        private int price;
        private int views;
        private String imgUrl;
        private String url;
        private String platform;

        @Builder
        public ResponseSearchItem(Long id, String brand, String name, String description, int price, int views, String imgUrl, String url, String platform) {
            this.id = id;
            this.brand = brand;
            this.name = name;
            this.description = description;
            this.price = price;
            this.views = views;
            this.imgUrl = imgUrl;
            this.url = url;
            this.platform = platform;
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
                    ", imgUrl=" + imgUrl +
                    ", url=" + url +
                    ", platform='" + platform + '\'' +
                    '}';
        }
    }

}
