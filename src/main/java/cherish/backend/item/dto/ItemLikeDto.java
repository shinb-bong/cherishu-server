package cherish.backend.item.dto;

import cherish.backend.item.model.Item;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemLikeDto {
    private final Long itemId;
    private final String name;
    private final String imgUrl;
    private final int price;
    private final boolean like;
    private final String brand;


    // 변환 메소드
    public static ItemLikeDto of(Item item){
        return ItemLikeDto.builder()
                .itemId(item.getId())
                .name(item.getName())
                .brand(item.getBrand())
                .imgUrl(item.getImgUrl())
                .price(item.getPrice())
                .like(true).build();

    }
}
