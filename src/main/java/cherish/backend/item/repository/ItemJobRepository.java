package cherish.backend.item.repository;

import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemJob;
import cherish.backend.member.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJobRepository extends JpaRepository<ItemJob, Long> {
    boolean existsByItemAndJob(Item item, Job job);
}
