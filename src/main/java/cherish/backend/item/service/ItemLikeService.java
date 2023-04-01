package cherish.backend.item.service;

import cherish.backend.item.constant.ItemConstant;
import cherish.backend.item.dto.ItemLikeDto;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemLike;
import cherish.backend.item.repository.ItemLikeRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.member.constant.Constants;
import cherish.backend.member.model.Member;
import cherish.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemLikeRepository itemLikeRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    // 생성
    @Transactional
    public Long likeItem(String email, Long itemId){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException(Constants.MEMBER_NOT_FOUND));
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
    public List<ItemLikeDto> getLikeItem(String email){
        List<Item> itemLike = itemLikeRepository.findItemLike(email);
        List<ItemLikeDto> itemList = itemLike.stream().map(ItemLikeDto::of).collect(Collectors.toList());
        return itemList;
    }
}
