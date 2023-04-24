package cherish.backend.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class ItemSearchResponseDto {
        private Long id;
        private String name;
        private String brand;
        private String description;
        private int price;
        private String imgUrl;
        private boolean isLiked;

        @QueryProjection
        public ItemSearchResponseDto(Long id, String name, String brand, String description, int price, String imgUrl, boolean isLiked) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.description = description;
            this.price = price;
            this.imgUrl = imgUrl;
            this.isLiked = isLiked;
        }
}
