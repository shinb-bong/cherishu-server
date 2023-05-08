package cherish.backend.board.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MonthlyCurationQueryDto {
    private long id;
    private String title;
    private String subtitle;
    private String img;
    private long itemId;
    private String name;
    private String brand;
    private String description;
    private int price;
    private String imgUrl;
    private boolean like;

    @QueryProjection
    public MonthlyCurationQueryDto(long id, String title, String subtitle, String img, long itemId, String name, String brand, String description, int price, String imgUrl, boolean like) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.img = img;
        this.itemId = itemId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.like = like;
    }
}
