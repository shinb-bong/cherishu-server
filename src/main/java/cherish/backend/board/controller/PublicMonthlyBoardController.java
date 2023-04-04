package cherish.backend.board.controller;

import cherish.backend.auth.security.CurrentUser;
import cherish.backend.board.dto.MonthlyCurationRequestDto;
import cherish.backend.board.dto.MonthlyCurationResponseDto;
import cherish.backend.board.service.MonthlyBoardService;
import cherish.backend.member.model.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<MonthlyCurationResponseDto> monthlyCuration(@Valid MonthlyCurationRequestDto dto, @CurrentUser Member member) {
        return monthlyBoardService.getMonthlyCurationList(dto.getYear(), dto.getMonth(), member);
    }
}
