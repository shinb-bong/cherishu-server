package cherish.backend.category.model;

import cherish.backend.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
        parent.getChildren().add(this);
    }

    public Category getParent() {
        if (this.parent == null) {
            return null;
        }
        return this.parent;
    }

    public String getParentName() {
        Category parentCategory = getParent();
        if (parentCategory == null) {
            return null;
        }
        return parentCategory.getName();
    }

}
