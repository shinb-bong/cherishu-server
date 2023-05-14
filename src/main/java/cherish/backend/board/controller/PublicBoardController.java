package cherish.backend.board.controller;

import cherish.backend.board.dto.BoardResponseDto;
import cherish.backend.board.service.BoardService;
import cherish.backend.common.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/public/board")
@RestController
public class PublicBoardController {
    private final BoardService boardService;

    @GetMapping
    public PageResponse<BoardResponseDto> getBoard(Pageable pageable) {
        return new PageResponse<>(boardService.getBoards(pageable));
    }
}
