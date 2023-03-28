package cherish.backend.item.repository;

import cherish.backend.item.model.ItemFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemUrlRepository extends JpaRepository<ItemFilter, Long> {
}
