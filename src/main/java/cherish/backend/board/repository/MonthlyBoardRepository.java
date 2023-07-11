package cherish.backend.board.repository;

import cherish.backend.board.model.MonthlyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MonthlyBoardRepository extends JpaRepository<MonthlyBoard, Long> {
    @Query("SELECT mb FROM MonthlyBoard mb ORDER BY mb.year DESC, mb.month DESC LIMIT 1")
    Optional<MonthlyBoard> findLatestMonthlyBoard();

}
