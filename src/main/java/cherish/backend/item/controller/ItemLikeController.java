package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemLikeDto;
import cherish.backend.item.dto.ItemLikeRequest;
import cherish.backend.item.service.ItemLikeService;
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
    @PostMapping("/like")
    public Long itemLike(@RequestBody ItemLikeRequest request){
        return itemLikeService.likeItem(request.getEmail(), request.getItemId());
    }

    @DeleteMapping("/like")
    public void deleteItemLike(@RequestBody ItemLikeRequest request){
        itemLikeService.deleteLikeItem(request.getItemId(), request.getEmail());
    }

    @GetMapping("/like/{email}")
    public List<ItemLikeDto> getItemLike(@PathVariable("email") String email){
        return itemLikeService.getLikeItem(email);
    }
}
