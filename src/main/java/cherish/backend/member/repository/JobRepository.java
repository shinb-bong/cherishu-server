package cherish.backend.member.repository;


import cherish.backend.member.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job,Long> {
    Optional<Job> findByName(String name);
}
