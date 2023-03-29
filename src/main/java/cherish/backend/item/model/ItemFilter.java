package cherish.backend.item.model;

import cherish.backend.category.model.Filter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemFilter {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id")
    private Filter filter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String name;

    public static ItemFilter createItemFilter(Filter filter, Item item, String name) {
        return ItemFilter.builder()
                .filter(filter)
                .item(item)
                .name(name)
                .build();
    }

}
