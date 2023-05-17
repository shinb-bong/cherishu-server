package cherish.backend.item.service;

import cherish.backend.item.dto.RecommendKeywordResponseDto;
import cherish.backend.item.repository.RecommendKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecommendKeywordService {
    private final RecommendKeywordRepository repository;

    public RecommendKeywordResponseDto getKeywords() {
        return new RecommendKeywordResponseDto(repository.getKeywords());
    }
}
