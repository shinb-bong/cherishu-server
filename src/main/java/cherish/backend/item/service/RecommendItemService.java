package cherish.backend.item.service;

import cherish.backend.item.dto.RecommendItemQueryDto;
import cherish.backend.item.dto.RecommendItemResponseDto;
import cherish.backend.item.repository.RecommendItemRepository;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendItemService {

    private final RecommendItemRepository recommendItemRepository;

    public List<RecommendItemResponseDto> getRecommendItem(Member member) {
        List<RecommendItemQueryDto> recommendItemList = recommendItemRepository.getRecommendItemList(member);

        Map<Long, List<RecommendItemQueryDto>> recommendItemMap = recommendItemList.stream()
                .collect(Collectors.groupingBy(RecommendItemQueryDto::getRecommendId));
        List<RecommendItemResponseDto> result = recommendItemMap.entrySet().stream()
                .map(entry -> {
                    List<RecommendItemResponseDto.RecommendItemDto> recommendItemDtos = entry.getValue().stream()
                            .map(RecommendItemResponseDto.RecommendItemDto::item)
                            .toList();

                    RecommendItemQueryDto itemQueryDto = entry.getValue().get(0);
                    RecommendItemResponseDto responseDto = new RecommendItemResponseDto();
                    responseDto.setTitle(itemQueryDto.getTitle());
                    responseDto.setSubtitle(itemQueryDto.getSubtitle());
                    responseDto.setRecommendItemList(recommendItemDtos);
                    responseDto.setBannerUrl(entry.getValue().get(0).getBannerUrl());

                    return responseDto;
                })
                .toList();

        return result;
    }
}
