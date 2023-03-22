package cherish.backend.category.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "filter")
public class Filter {

    @Id
    @GeneratedValue
    private Long id;

    private String name; // filter name(나이, 감정, 상황)

    public Filter(String name) {
        this.name = name;
    }
}
