package cherish.backend.item.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class RecommendItemResponseDto {
    private String bannerUrl;
    private String title;
    private String subtitle;
    private String keywordParameter;
    private List<RecommendItemDto> recommendItemList = new ArrayList<>();

    @Builder
    @Data
    public static class RecommendItemDto {
        private Long itemId;
        private String name;
        private String brand;
        private int price;
        private String imgUrl;
        private boolean like;

        public static RecommendItemDto item(RecommendItemQueryDto itemQueryDto) {
            return RecommendItemDto.builder()
                    .itemId(itemQueryDto.getItemId())
                    .name(itemQueryDto.getName())
                    .brand(itemQueryDto.getBrand())
                    .price(itemQueryDto.getPrice())
                    .imgUrl(itemQueryDto.getImgUrl())
                    .like(itemQueryDto.isLike())
                    .build();
        }
    }
}
