package cherish.backend.item.model;

import cherish.backend.category.model.Filter;
import cherish.backend.item.repository.ItemFilterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ItemFilterTest {

    @Mock
    ItemFilterRepository itemFilterRepository;

    @Test
    void filter() {

        //given
        Filter situation = new Filter("situation");
        Filter emotion = new Filter("emotion");

        Item item1 = Item.builder().name("이솝").price(1000).build();
        Item item2 = Item.builder().name("논픽션").price(2000).build();

        ItemFilter itemFilter1 = ItemFilter.createItemFilter(situation, item1, "이사");
        ItemFilter itemFilter2 = ItemFilter.createItemFilter(emotion, item2, "축하");

        given(itemFilterRepository.findItemFilterByNameAndFilterId(eq(itemFilter1.getName()), eq(itemFilter1.getFilter().getId()))).willReturn(Collections.singletonList(itemFilter1));
        given(itemFilterRepository.findItemFilterByNameAndFilterId(eq(itemFilter2.getName()), eq(itemFilter2.getFilter().getId()))).willReturn(Collections.singletonList(itemFilter2));

        //when
        List<ItemFilter> situationFilter = itemFilterRepository.findItemFilterByNameAndFilterId(itemFilter1.getName(), itemFilter1.getFilter().getId());
        List<ItemFilter> emotionFilter = itemFilterRepository.findItemFilterByNameAndFilterId(itemFilter2.getName(), itemFilter2.getFilter().getId());

        //then
        assertThat(situationFilter.get(0).getFilter().getId()).isEqualTo(situation.getId());
        assertThat(emotionFilter.get(0).getFilter().getId()).isEqualTo(emotion.getId());

    }
}
