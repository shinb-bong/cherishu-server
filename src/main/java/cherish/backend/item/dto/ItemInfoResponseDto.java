package cherish.backend.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String brandUrl;
    private String kakaoUrl;
    private String coupangUrl;
    private String naverUrl;
    private List<String> tagList;

    private Long itemLikeId;

    @QueryProjection
    public ItemInfoResponseDto(Long itemId, String name, String brand, String description, int price, String imgUrl, int views, Long itemLikeId) {
        this.itemId = itemId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.views = views;
        this.itemLikeId = itemLikeId;
    }
}