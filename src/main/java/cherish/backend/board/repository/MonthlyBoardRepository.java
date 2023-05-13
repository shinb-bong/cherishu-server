package cherish.backend.board.repository;

import cherish.backend.board.model.MonthlyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MonthlyBoardRepository extends JpaRepository<MonthlyBoard, Long> {
    @Query("SELECT mb FROM MonthlyBoard mb WHERE mb.id IN "
            + "(SELECT MIN(mb2.id) FROM MonthlyBoard mb2 WHERE mb2.month IN :months GROUP BY mb2.month)")
    Optional<MonthlyBoard> findMonthlyBoardByMonths(@Param("months") List<Integer> months);

}
