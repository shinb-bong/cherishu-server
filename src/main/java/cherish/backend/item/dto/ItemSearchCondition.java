package cherish.backend.item.dto;

import lombok.Data;

@Data
public class ItemSearchCondition {
    private Long filterId;
    private String itemFilterName;
    private String itemCategoryParent;
    private String itemCategoryChildren;
    private String itemJobParent;
    private String itemJobChildren;
    private String itemName;
    private String itemBrand;
    private String keyword;
    private String target;
}
