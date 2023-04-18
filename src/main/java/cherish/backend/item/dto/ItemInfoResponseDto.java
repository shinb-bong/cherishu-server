package cherish.backend.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
    private List<String> tagList;
    private Map<String, String> url;
    private boolean isLiked;
    private Long memberId;

    @QueryProjection
    public ItemInfoResponseDto(Long itemId, String name, String brand, String description, int price, String imgUrl, int views, boolean isLiked, Long memberId) {
        this.itemId = itemId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.views = views;
        this.isLiked = isLiked;
        this.memberId = memberId;
    }
}