package cherish.backend.curation.service;

import cherish.backend.curation.dto.CurationRequestParam;
import cherish.backend.curation.dto.CurationResponseDto;
import cherish.backend.curation.repository.CurationRepository;
import cherish.backend.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurationService {
    private final CurationRepository curationRepository;

    public List<CurationResponseDto> getCurationItems(CurationRequestParam param, Member member) {
        long memberId = member != null ? member.getId() : -1;
        var list = curationRepository.findCurationItemsBy(
            param.age(),
            param.minPrice(),
            param.maxPrice(),
            param.job(),
            param.category(),
            param.gender(),
            param.purpose(),
            param.relation(),
            param.emotion(),
            memberId
        );

        return list.stream()
            .map(CurationResponseDto::ofTuple)
            .toList();
    }
}
