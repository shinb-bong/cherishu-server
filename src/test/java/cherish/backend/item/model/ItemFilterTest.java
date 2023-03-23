package cherish.backend.item.model;

import cherish.backend.category.model.Filter;
import cherish.backend.item.dto.ItemFilterCondition;
import cherish.backend.item.dto.ItemFilterQueryDto;
import cherish.backend.item.repository.ItemFilterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ItemFilterTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemFilterRepository itemFilterRepository;

    @Test
    void filter() {

        //given
        Filter situation = new Filter("situation");
        em.persist(situation);

        Item item1 = Item.builder().name("이솝").price(1000).build();
        em.persist(item1);

        ItemFilter itemFilter1 = ItemFilter.createItemFilter(situation, item1, "이사");
        em.persist(itemFilter1);

        ItemFilterCondition condition = new ItemFilterCondition();
        condition.setFilterId(itemFilter1.getFilter().getId());
        condition.setItemFilterName(itemFilter1.getName());

        //when
        List<ItemFilterQueryDto> itemFilterByNameAndId = itemFilterRepository.findItemFilterByNameAndId(condition);

        //then
        assertThat(itemFilterByNameAndId).extracting("itemFilterId").containsExactly(situation.getId());
        assertThat(itemFilterByNameAndId).extracting("itemFilterName").containsExactly(itemFilter1.getName());

    }
}
