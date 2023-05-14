package cherish.backend.board.service;

import cherish.backend.board.dto.BoardResponseDto;
import cherish.backend.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<BoardResponseDto> getBoards(Pageable pageable) {
        var list = boardRepository.findAllByOrderByCreatedDateDesc(pageable);
        return list
            .map(b -> BoardResponseDto.builder()
                .title(b.getTitle())
                .content(b.getContent())
                .createdDate(b.getCreatedDate())
                .build());
    }
}
