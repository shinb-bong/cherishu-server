package cherish.backend.item.model;

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
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String brand;
    private String name;
    private int price;
    private ItemType itemType;

}
