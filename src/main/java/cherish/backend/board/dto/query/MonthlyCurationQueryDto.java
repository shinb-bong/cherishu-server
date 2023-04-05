package cherish.backend.board.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MonthlyCurationQueryDto {
    private long id;
    private String title;
    private String subtitle;
    private String img;
    private String name;
    private String brand;
    private String description;
    private int price;
    private boolean like;

    @QueryProjection
    public MonthlyCurationQueryDto(long id, String title, String subtitle, String img, String name, String brand, String description, int price, boolean like) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.img = img;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.like = like;
    }
}
