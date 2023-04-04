package cherish.backend.board.controller;

import cherish.backend.auth.security.CurrentUser;
import cherish.backend.board.dto.MonthlyCurationResponseDto;
import cherish.backend.board.service.MonthlyBoardService;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/public/monthly")
@RestController
public class PublicMonthlyBoardController {
    private final MonthlyBoardService monthlyBoardService;

    @GetMapping("/curation/{year}/{month}")
    public List<MonthlyCurationResponseDto> monthlyCuration(@PathVariable int year, @PathVariable int month, @CurrentUser Member member) {
        return monthlyBoardService.getMonthlyCurationList(year, month, member);
    }
}
