package cherish.backend.board.controller;

import cherish.backend.board.dto.BoardResponseDto;
import cherish.backend.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/public/board")
@RestController
public class PublicBoardController {
    private final BoardService boardService;

    @GetMapping
    public List<BoardResponseDto> getBoard(Pageable pageable) {
        return boardService.getBoards(pageable);
    }
}
