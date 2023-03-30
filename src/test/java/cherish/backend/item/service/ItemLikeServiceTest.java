package cherish.backend.item.service;

import cherish.backend.item.dto.ItemLikeDto;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemLike;
import cherish.backend.item.repository.ItemLikeRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.member.model.Member;
import cherish.backend.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemLikeServiceTest {

    @Autowired
    ItemLikeService service;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemLikeRepository repository;

    @Test
    public void 좋아요하기(){
        //given
        String email ="test@naver.com";
        Member member = Member.builder()
                .email(email)
                .name("cherishu")
                .build();

        Item item = Item.builder().id(3L).name("끌레도르").brand("cu").build();
        memberRepository.save(member);
        itemRepository.save(item);
        //when
        Long likeItemId = service.likeItem(email, 3L);
        //when
        ItemLike itemLike = repository.findById(likeItemId).orElseThrow(() -> new IllegalStateException());
        //then
        assertThat(itemLike.getMember().getName()).isEqualTo(member.getName());
        assertThat(itemLike.getItem().getId()).isEqualTo(item.getId());
    }

    @Test
    void 삭제하기(){
        String email ="test@naver.com";
        Member member = Member.builder()
                .email(email)
                .name("cherishu")
                .build();
        Item item = Item.builder().id(3L).name("끌레도르").brand("cu").build();
        memberRepository.save(member);
        itemRepository.save(item);
        //when
        Long itemLikeId = service.likeItem(email, 3L);
        service.deleteLikeItem(item.getId(),email);
        assertThatThrownBy(()->repository.findById(itemLikeId).get()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void 좋아요_리스트_가져오기(){
        //given
        String email ="test@naver.com";
        Member member = Member.builder()
                .email(email)
                .name("cherishu")
                .build();
        Item item = Item.builder().id(3L).name("끌레도르").brand("cu").imgUrl("img.url").price(5000).build();
        memberRepository.save(member);
        itemRepository.save(item);
        //when
        Long itemLikeId = service.likeItem(email, item.getId());
        List<ItemLikeDto> likeItem = service.getLikeItem(email);
        //then
        assertThat(likeItem.get(0).isItemLike()).isTrue();
        assertThat(likeItem.get(0).getBrand()).isEqualTo(item.getBrand());
    }
}