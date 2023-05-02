package cherish.backend.board.dto;

import cherish.backend.board.dto.query.MonthlyCurationQueryDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MonthlyCurationResponseDto {
    private int year;
    private int month;
    private String title;
    private String subtitle;
    private String imgUrl;
    private List<CurationItem> itemList;

    @Builder
    @Data
    public static class CurationItem {
        private String name;
        private String brand;
        private String description;
        private int price;
        private String imgUrl;
        private boolean like;

        public static CurationItem of(MonthlyCurationQueryDto queryDto) {
            return CurationItem.builder()
                .name(queryDto.getName())
                .brand(queryDto.getBrand())
                .description(queryDto.getDescription())
                .price(queryDto.getPrice())
                .imgUrl(queryDto.getImgUrl())
                .like(queryDto.isLike())
                .build();
        }
    }
}
