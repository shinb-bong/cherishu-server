package cherish.backend.item.model;

import cherish.backend.member.model.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemLikeTest {
    @Autowired
    EntityManager em;

    @Test
    void 아이템_좋아요_생성(){
        //given
        Member member = Member.builder()
                .email("test1@naver.com")
                .name("cherishu")
                .build();
        Item item = Item.builder().id(3L).name("끌레도르").brand("cu").build();
        em.persist(member);
        em.persist(item);
        //when
        ItemLike itemLike1 = ItemLike.createItemLike(member, item);
        em.persist(itemLike1);
        em.flush();
        em.clear();
        ItemLike itemLike2 = em.find(ItemLike.class, itemLike1.getId());
        //then
        assertThat(itemLike1.getMember().getName()).isEqualTo(itemLike2.getMember().getName());
        assertThat(itemLike1.getItem().getName()).isEqualTo(itemLike2.getItem().getName());
        assertThat(itemLike1).isInstanceOf(ItemLike.class);
    }

}