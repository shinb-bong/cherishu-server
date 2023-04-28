package cherish.backend.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RecommendItemQueryDto {
    private Long recommendId;
    private String bannerUrl;
    private Long itemId;
    private String name;
    private String brand;
    private int price;
    private String imgUrl;
    private boolean like;
    private String title;
    private String subtitle;

    @QueryProjection
    public RecommendItemQueryDto(Long recommendId, String bannerUrl, Long itemId, String name, String brand, int price, String imgUrl, boolean like, String title, String subtitle) {
        this.recommendId = recommendId;
        this.bannerUrl = bannerUrl;
        this.itemId = itemId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imgUrl = imgUrl;
        this.like = like;
        this.title = title;
        this.subtitle = subtitle;
    }
}
