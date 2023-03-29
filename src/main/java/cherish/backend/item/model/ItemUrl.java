package cherish.backend.item.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemUrl {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private String url;

    private String platform;

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
