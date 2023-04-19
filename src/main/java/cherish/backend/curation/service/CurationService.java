package cherish.backend.curation.service;

import cherish.backend.curation.dto.CurationRequestParam;
import cherish.backend.curation.dto.query.CurationQueryDto;
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

    public List<CurationQueryDto> getCurationItems(CurationRequestParam param, Member member) {
        // TODO: member id로 like 여부 가져오기
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
            param.emotion()
        );

        return list.stream()
            .map(t -> new CurationQueryDto(
                t.get(0, Long.class),
                t.get(1, Long.class)
            ))
            .toList();
    }
}
