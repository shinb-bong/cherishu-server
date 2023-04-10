package cherish.backend.item.service;

import cherish.backend.item.constant.ItemConstant;
import cherish.backend.item.dto.ItemLikeDto;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemLike;
import cherish.backend.item.repository.ItemLikeRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemLikeRepository itemLikeRepository;
    private final ItemRepository itemRepository;

    // 생성
    @Transactional
    public Long likeItem(Member member, Long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException(ItemConstant.ITEM_NOT_FOUND));
        ItemLike itemLike = ItemLike.createItemLike(member, item);
        itemLikeRepository.save(itemLike);
        return itemLike.getId();
    }
    // 삭제
    @Transactional
    public void deleteLikeItem(Long itemId, String email){
        Long itemLikeId = itemLikeRepository.findItemLikeId(itemId, email);
        itemLikeRepository.deleteById(itemLikeId);
    }

    // 회원별 좋아하는 아이템 가져오기
    public List<ItemLikeDto> getLikeItem(Member member) {
        List<Item> itemLike = itemLikeRepository.findItemLike(member);
        return itemLike.stream().map(ItemLikeDto::of).toList();
    }
}
