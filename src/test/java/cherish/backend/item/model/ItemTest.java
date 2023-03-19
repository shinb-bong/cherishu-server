package cherish.backend.item.model;

import cherish.backend.item.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemTest {
    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    @Test
    public void BaseTimeEntity_등록() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        //when
        itemRepository.save(Item.builder()
                .brand("샤넬")
                .price(1000000).build());
        List<Item> itemList = itemRepository.findAll();
        Item item = itemList.get(0);
        //then
        Assertions.assertThat(item.getCreatedDate()).isAfter(now);
        Assertions.assertThat(item.getModifiedDate()).isAfter(now);
    }

}