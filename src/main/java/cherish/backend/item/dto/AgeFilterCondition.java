package cherish.backend.item.dto;

import cherish.backend.category.model.Filter;
import lombok.Data;

@Data
public class AgeFilterCondition {
    private String itemFilterName;
    private Long filterId;
    private Integer ageGoe;
    private Integer ageLoe;
}
