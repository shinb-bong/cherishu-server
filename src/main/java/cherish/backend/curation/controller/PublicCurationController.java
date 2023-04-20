package cherish.backend.curation.controller;

import cherish.backend.auth.security.CurrentUser;
import cherish.backend.curation.dto.CurationRequestParam;
import cherish.backend.curation.dto.CurationResponseDto;
import cherish.backend.curation.service.CurationService;
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
@RequestMapping("/public/curation")
@RestController
public class PublicCurationController {
    private final CurationService curationService;

    @GetMapping
    public List<CurationResponseDto> curation(@Valid CurationRequestParam param, @CurrentUser Member member) {
        return curationService.getCurationItems(param, member);
    }
}
