package cherish.backend.item.model;

import cherish.backend.common.model.BaseTimeEntity;
import cherish.backend.item.model.enums.ItemType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String brand;
    private String name;
    private String description;
    private int price;
    private int views;
    private String img_url;

    @Builder
    public Item(String brand, String name, String description, int price, int views, String img_url) {
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.price = price;
        this.views = views;
        this.img_url = img_url;
    }
}
