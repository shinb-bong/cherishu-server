package cherish.backend.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class ItemInfoViewDto {
    private Long itemId;
    private String name;
    private String brand;
    private String description;
    private int price;
    private String imgUrl;
    private int views;
    private List<String> platforms;
    private Map<String, String> urls;
    private List<String> filterTags;
    private String categoryTag;
    private boolean isLiked;

    public ItemInfoViewDto(ItemInfoResponseDto itemInfoResponseDto) {
        this.itemId = itemInfoResponseDto.getItemId();
        this.name = itemInfoResponseDto.getName();
        this.brand = itemInfoResponseDto.getBrand();
        this.description = itemInfoResponseDto.getDescription();
        this.price = itemInfoResponseDto.getPrice();
        this.imgUrl = itemInfoResponseDto.getImgUrl();
        this.views = itemInfoResponseDto.getViews();
        this.platforms = new ArrayList<>(Arrays.asList(itemInfoResponseDto.getPlatform().split(", ")));
        this.urls = getUrls(itemInfoResponseDto);
        this.filterTags = Arrays.asList(itemInfoResponseDto.getFilterTag().split(", "));
        this.categoryTag = itemInfoResponseDto.getCategoryTag();
        this.isLiked = itemInfoResponseDto.isLiked();
    }

    public List<String> getFilterTags() {
        return filterTags.stream()
                .map(tag -> tag.replaceAll("\\[|\\]", "").trim())
                .toList();
    }

    public Map<String, String> getUrls(ItemInfoResponseDto itemInfoResponseDto) {
        Map<String, String> urls = new HashMap<>();
        String[] platforms = itemInfoResponseDto.getPlatform().split(", ");
        String[] urlsArr = itemInfoResponseDto.getUrl().split(", ");
        for (int i = 0; i < platforms.length; i++) {
            urls.put(platforms[i], urlsArr[i]);
        }
        return urls;
    }

}
