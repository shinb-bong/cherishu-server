package cherish.backend.item.repository;

import cherish.backend.item.model.ItemLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLikeRepository extends JpaRepository<ItemLike,Long>, ItemLikeRepositoryCustom{
}