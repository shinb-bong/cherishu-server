package cherish.backend.item.model;

import cherish.backend.category.model.Filter;
import cherish.backend.item.dto.AgeFilterCondition;
import cherish.backend.item.dto.AgeFilterQueryDto;
import cherish.backend.item.dto.ItemFilterCondition;
import cherish.backend.item.dto.ItemFilterQueryDto;
import cherish.backend.item.repository.ItemFilterRepository;
import cherish.backend.item.repository.ItemRepository;
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

    @Test
    void age_Goe_age_Loe() {
        // given
        Filter age = new Filter("age");
        em.persist(age);

        Item item1 = Item.builder().name("이솝").minAge(25).maxAge(30).build();
        Item item2 = Item.builder().name("논픽션").minAge(20).maxAge(24).build();
        Item item3 = Item.builder().name("르라보").minAge(30).maxAge(45).build();
        em.persist(item1);
        em.persist(item2);
        em.persist(item3);

        ItemFilter itemFilter1 = ItemFilter.createItemFilter(age, item1, String.valueOf(27));
        ItemFilter itemFilter2 = ItemFilter.createItemFilter(age, item2, String.valueOf(20));
        ItemFilter itemFilter3 = ItemFilter.createItemFilter(age, item3, String.valueOf(34));
        em.persist(itemFilter1);
        em.persist(itemFilter2);
        em.persist(itemFilter3);

        em.flush();
        em.clear();

        // when
        AgeFilterCondition ageCondition = new AgeFilterCondition();
        ageCondition.setAgeLoe(item2.getMaxAge());
        ageCondition.setAgeGoe(item2.getMinAge());
        ageCondition.setFilterId(age.getId());
        ageCondition.setItemFilterName(itemFilter2.getName());
        List<AgeFilterQueryDto> result = itemFilterRepository.findItemFilterByAge(ageCondition);

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getItemName()).isEqualTo(item2.getName());
        assertThat(result.get(0).getMinAge()).isEqualTo(item2.getMinAge());
        assertThat(result.get(0).getMaxAge()).isEqualTo(item2.getMaxAge());
    }

}
