package cherish.backend.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ItemSearchQueryDto {
    private Long filterId;
    private Long itemFilterId;
    private Long itemId;
    private Long categoryId;
    private Long itemCategoryId;
    private Long jobId;
    private Long itemJobId;
    private String filterName;
    private String itemFilterName;
    private String categoryParent;
    private String categoryChildren;
    private String jobParent;
    private String jobChildren;
    private String itemName;
    private String itemBrand;

    @QueryProjection
    public ItemSearchQueryDto(Long filterId, Long itemFilterId, Long itemId, Long categoryId, Long itemCategoryId, Long jobId, Long itemJobId, String filterName, String itemFilterName, String categoryParent, String categoryChildren, String jobParent, String jobChildren, String itemName, String itemBrand) {
        this.filterId = filterId;
        this.itemFilterId = itemFilterId;
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.itemCategoryId = itemCategoryId;
        this.jobId = jobId;
        this.itemJobId = itemJobId;
        this.filterName = filterName;
        this.itemFilterName = itemFilterName;
        this.categoryParent = categoryParent;
        this.categoryChildren = categoryChildren;
        this.jobParent = jobParent;
        this.jobChildren = jobChildren;
        this.itemName = itemName;
        this.itemBrand = itemBrand;
    }
}
