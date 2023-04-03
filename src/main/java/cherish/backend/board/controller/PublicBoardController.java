package cherish.backend.board.controller;

import cherish.backend.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/public/board")
@RestController
public class PublicBoardController {
    private final BoardService boardService;
}
