package cherish.backend.board.service;

import cherish.backend.board.dto.MonthlyCurationResponseDto;
import cherish.backend.board.dto.query.MonthlyCurationQueryDto;
import cherish.backend.board.dto.query.condition.MonthlyCurationCondition;
import cherish.backend.board.repository.MonthlyBoardRepository;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MonthlyBoardService {
    private final MonthlyBoardRepository monthlyBoardRepository;

    public List<MonthlyCurationResponseDto> getMonthlyCurationList(int year, int month, Member member) {
        List<MonthlyCurationResponseDto> result = new ArrayList<>();

        MonthlyCurationCondition condition = MonthlyCurationCondition.builder()
            .year(year)
            .month(month)
            .member(member)
            .build();
        var list = monthlyBoardRepository.searchMonthlyCurationByYearAndMonth(condition);
        log.debug("list: {}", list);

        list.stream()
            .collect(Collectors.groupingBy(MonthlyCurationQueryDto::getId))
            .forEach((key, value) -> {
                MonthlyCurationQueryDto first = value.get(0);
                result.add(
                    MonthlyCurationResponseDto.builder()
                        .year(condition.getYear())
                        .month(condition.getMonth())
                        .title(first.getTitle())
                        .subtitle(first.getSubtitle())
                        .imgUrl(first.getImg())
                        .itemList(
                            value.stream()
                                .map(MonthlyCurationResponseDto.CurationItem::of)
                                .toList()
                        )
                        .build()
                );
            });

        return result;
    }
}
