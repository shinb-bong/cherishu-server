package cherish.backend.curation.controller;

import cherish.backend.curation.dto.CurationRequestParam;
import cherish.backend.curation.service.CurationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/public/curation")
@RestController
public class PublicCurationController {
    private final CurationService curationService;

    @GetMapping
    public void curation(@Valid CurationRequestParam param) {

    }
}
