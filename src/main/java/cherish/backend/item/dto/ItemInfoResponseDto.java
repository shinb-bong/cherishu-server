package cherish.backend.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemInfoResponseDto {
    private Long itemId;
    private String name;
    private String brand;
    private String description;
    private int price;
    private String imgUrl;
    private int views;
    private String platform;
    private String url;
    private String filterTag;
    private String categoryTag;
    private boolean isLiked;
    @QueryProjection
    public ItemInfoResponseDto(Long itemId, String name, String brand, String description, int price, String imgUrl, int views, String platform, String url, String filterTag, String categoryTag, boolean isLiked) {
        this.itemId = itemId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.views = views;
        this.platform = platform;
        this.url = url;
        this.filterTag = filterTag;
        this.categoryTag = categoryTag;
        this.isLiked = isLiked;
    }
}