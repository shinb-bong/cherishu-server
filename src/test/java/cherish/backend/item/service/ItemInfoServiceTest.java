package cherish.backend.item.service;

import cherish.backend.category.model.Category;
import cherish.backend.category.model.Filter;
import cherish.backend.category.repository.CategoryRepository;
import cherish.backend.category.repository.FilterRepository;
import cherish.backend.item.constant.ItemUrlPlatforms;
import cherish.backend.item.dto.ItemInfoResponseDto;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemCategory;
import cherish.backend.item.model.ItemFilter;
import cherish.backend.item.model.ItemUrl;
import cherish.backend.item.repository.ItemCategoryRepository;
import cherish.backend.item.repository.ItemFilterRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.item.repository.ItemUrlRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
public class ItemInfoServiceTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;
    @Autowired
    private FilterRepository filterRepository;
    @Autowired
    private ItemFilterRepository itemFilterRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ItemUrlRepository itemUrlRepository;

    @Transactional
    @Test
    void itemInfoReturnTest() {
        Filter preference = new Filter("preference");
        filterRepository.save(preference);

        Item item = Item.builder().id(2003L).name("name").brand("brand").views(1).build();
        itemRepository.save(item);

        ItemFilter itemFilter = ItemFilter.createItemFilter(preference, item, "이사");
        itemFilterRepository.save(itemFilter);

        Category category = Category.builder().name("뷰티").build();
        categoryRepository.save(category);

        ItemCategory itemCategory = ItemCategory.builder().category(category).item(item).build();
        itemCategoryRepository.save(itemCategory);

        ItemUrl itemUrl = ItemUrl.builder().item(item).url("brandUrl").platform(ItemUrlPlatforms.BRAND).build();
        itemUrlRepository.save(itemUrl);
//
//        ItemInfoResponseDto response = itemService.findItemInfo(item.getId());
//
//        assertEquals(response.getItemId(), 2003);
    }
}