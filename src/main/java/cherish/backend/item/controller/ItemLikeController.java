package cherish.backend.item.controller;

import cherish.backend.auth.security.CurrentUser;
import cherish.backend.item.dto.ItemLikeDto;
import cherish.backend.item.service.ItemLikeService;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemLikeController {
    private final ItemLikeService itemLikeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/like/{itemId}")
    public Long itemLike(@PathVariable Long itemId, @CurrentUser Member member){
        return itemLikeService.likeItem(member, itemId);
    }

    @DeleteMapping("/like/{itemId}")
    public void deleteItemLike(@PathVariable Long itemId, @CurrentUser Member member){
        itemLikeService.deleteLikeItem(itemId, member.getEmail());
    }

    @GetMapping("/like")
    public List<ItemLikeDto> getItemLike(@CurrentUser Member member){
        return itemLikeService.getLikeItem(member.getEmail());
    }
}
