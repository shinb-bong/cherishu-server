package cherish.backend.curation.service;

import cherish.backend.curation.repository.CurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurationService {
    private final CurationRepository curationRepository;
}
