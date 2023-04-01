package cherish.backend.item.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ItemSearchCondition {
    private String filterName;
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
