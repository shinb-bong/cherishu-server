package cherish.backend.item.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemSearchCondition {
    private String filterName;
    private String itemFilterName;
    private String itemCategoryParent;
    private List<String> itemCategoryChildren;
    private String itemJobParent;
    private List<String> itemJobChildren;
    private String itemName;
    private String itemBrand;
    private String keyword;
    private String target;
}
