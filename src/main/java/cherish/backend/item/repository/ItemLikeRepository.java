package cherish.backend.item.repository;

import cherish.backend.item.model.ItemLike;
import cherish.backend.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemLikeRepository extends JpaRepository<ItemLike,Long>, ItemLikeRepositoryCustom{
    Optional<ItemLike> findItemLikeByItemIdAndMember(Long itemId, Member member);
}