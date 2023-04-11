package cherish.backend.item.service;

import cherish.backend.auth.jwt.JwtConfig;
import cherish.backend.auth.jwt.JwtProperties;
import cherish.backend.category.model.Filter;
import cherish.backend.category.repository.FilterRepository;
import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchResponseDto;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemFilter;
import cherish.backend.item.model.ItemJob;
import cherish.backend.item.repository.ItemFilterRepository;
import cherish.backend.item.repository.ItemJobRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.member.model.Job;
import cherish.backend.member.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
public class ItemSearchServiceTest {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private FilterRepository filterRepository;
    @Autowired
    private ItemFilterRepository itemFilterRepository;
    @Autowired
    private ItemJobRepository itemJobRepository;

    @Test
    public void testSearchItem() throws Exception {
        String keyword = "firstJob";

        Item item = Item.builder().id(1L).name("test").brand("test1").price(1).build();
        Item item2 = Item.builder().id(2L).name("test2").brand("test3").price(2).build();
        Job job = Job.builder().id(7L).name("학생").build();
        ItemJob itemJob = ItemJob.builder().job(job).item(item2).build();
        Filter filter = Filter.builder().id(2L).build();
        ItemFilter itemFilter = ItemFilter.createItemFilter(filter, item2, "이사");

        itemRepository.saveAll(List.of(item, item2));
        jobRepository.save(job);
        filterRepository.save(filter);
        itemFilterRepository.save(itemFilter);
        itemJobRepository.save(itemJob);

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setKeyword(keyword);
        condition.setEmotionName(itemFilter.getName());

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");

        Page<ItemSearchResponseDto> result = itemService.searchItem(condition, pageable);
        if (result == null) {
            System.out.println("Search result is null");
        } else {
            System.out.println("Number of search results: " + result.getTotalElements());
            System.out.println("Result" + result.getContent().get(0));
        }

        for (ItemSearchResponseDto searchItem : result.getContent()) {
            assertTrue(searchItem.getBrand().toLowerCase().contains("test3"));
        }
    }

}
