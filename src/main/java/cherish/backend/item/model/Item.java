package cherish.backend.item.model;

import cherish.backend.common.model.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Item extends BaseTimeEntity {
    @Id
    private Long id;

    private String brand;
    private String name;
    private String description;
    private int price;
    private int views;
    private String imgUrl;
    private int minAge;
    private int maxAge;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemFilter> itemFilters = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemJob> itemJobs = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemCategory> itemCategories = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemUrl> itemUrls = new ArrayList<>();
}
