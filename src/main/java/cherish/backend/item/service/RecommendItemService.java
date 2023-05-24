package cherish.backend.item.service;

import cherish.backend.board.model.MonthlyBoard;
import cherish.backend.board.repository.MonthlyBoardRepository;
import cherish.backend.item.dto.RecommendItemQueryDto;
import cherish.backend.item.dto.RecommendItemResponseDto;
import cherish.backend.item.repository.RecommendItemRepository;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendItemService {

    private final RecommendItemRepository recommendItemRepository;
    private final MonthlyBoardRepository monthlyBoardRepository;
    List<Integer> months = IntStream.rangeClosed(1, 12).boxed().toList();

    public List<RecommendItemResponseDto> getRecommendItem(Member member) {
        List<RecommendItemQueryDto> recommendItemList = recommendItemRepository.getRecommendItemList(member);

        Map<Long, List<RecommendItemQueryDto>> recommendItemMap = recommendItemList.stream()
                .collect(Collectors.groupingBy(RecommendItemQueryDto::getRecommendId));
        List<RecommendItemResponseDto> result = recommendItemMap.entrySet().stream()
                .map(entry -> {
                    List<RecommendItemResponseDto.RecommendItemDto> recommendItemDtos = entry.getValue().stream()
                            .map(RecommendItemResponseDto.RecommendItemDto::item)
                            .distinct()
                            .toList();

                    RecommendItemQueryDto itemQueryDto = entry.getValue().get(0);
                    RecommendItemResponseDto responseDto = new RecommendItemResponseDto();
                    responseDto.setTitle(itemQueryDto.getTitle());
                    responseDto.setSubtitle(itemQueryDto.getSubtitle());
                    responseDto.setRecommendItemList(recommendItemDtos);
                    responseDto.setKeywordParameter(itemQueryDto.getLinkParam());

                    // refactor 필요
                    Optional<MonthlyBoard> monthlyBoard = monthlyBoardRepository.findMonthlyBoardByMonths(Collections.singletonList(5));
                    responseDto.setBannerUrl(monthlyBoard.get().getImgUrl());
                    return responseDto;
                })
                .toList();

        return result;
    }
}
