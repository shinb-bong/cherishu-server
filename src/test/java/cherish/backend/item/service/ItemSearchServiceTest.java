package cherish.backend.item.service;

import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchResponseDto;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemJob;
import cherish.backend.item.repository.ItemJobRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.member.model.Job;
import cherish.backend.member.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private ItemJobRepository itemJobRepository;
    @Autowired
    private JobRepository jobRepository;

    @Test
    public void testSearchItem() throws Exception {
        String keyword = "firstJob";

        Item item = Item.builder().id(1L).name("test").brand("test1").price(1).build();
        Item item2 = Item.builder().id(2L).name("test2").brand("test3").price(2).build();
        Job job = Job.builder().id(1L).name("firstJob").build();
        ItemJob itemJob = ItemJob.builder().id(1L).item(item).job(job).build();

        itemRepository.saveAll(List.of(item, item2));
        jobRepository.save(job);
        itemJobRepository.save(itemJob);

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setKeyword(keyword);

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");

        Page<ItemSearchResponseDto> result = itemService.searchItem(condition, pageable);
        if (result == null) {
            System.out.println("Search result is null");
        } else {
            System.out.println("Number of search results: " + result.getTotalElements());
            System.out.println("Result" + result.getContent().get(0));
        }

        for (ItemSearchResponseDto searchItem : result.getContent()) {
            assertTrue(searchItem.getBrand().toLowerCase().contains("test1"));
        }
    }

}
