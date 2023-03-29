package cherish.backend.item.repository;

import cherish.backend.item.constant.ItemUrlPlatforms;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemUrlRepository extends JpaRepository<ItemUrl, Long> {
    Optional<ItemUrl> findByItemAndPlatform(Item item, String platform);

    default Optional<ItemUrl> findBrandUrlByItem(Item item) {
        return findByItemAndPlatform(item, ItemUrlPlatforms.BRAND);
    }

    default Optional<ItemUrl> findNaverUrlByItem(Item item) {
        return findByItemAndPlatform(item, ItemUrlPlatforms.NAVER);
    }

    default Optional<ItemUrl> findCoupangUrlByItem(Item item) {
        return findByItemAndPlatform(item, ItemUrlPlatforms.COUPANG);
    }

    default Optional<ItemUrl> findKakaoUrlByItem(Item item) {
        return findByItemAndPlatform(item, ItemUrlPlatforms.KAKAO);
    }
}
