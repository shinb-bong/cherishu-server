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
                    RecommendItemResponseDto responseDto = new RecommendItemResponseDto();
                    responseDto.setBannerUrl(entry.getValue().get(0).getBannerUrl());

                    RecommendItemQueryDto itemQueryDto = entry.getValue().get(0);
                    responseDto.setTitle(itemQueryDto.getTitle());
                    responseDto.setSubtitle(itemQueryDto.getSubtitle());

                    List<RecommendItemResponseDto.RecommendItemDto> recommendItemDtos = entry.getValue().stream()
                            .map(RecommendItemResponseDto.RecommendItemDto::item)
                            .collect(Collectors.toList());
                    responseDto.setRecommendItemList(recommendItemDtos);

                    return responseDto;
                })
                .toList();

        return result;
    }

}
