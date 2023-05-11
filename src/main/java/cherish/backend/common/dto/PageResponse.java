package cherish.backend.common.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T> {

    private List<T> data;
    private PageInfo pageInfo;

    public PageResponse(Page<T> page) {
        this.data = page.getContent();
        this.pageInfo = PageInfo.builder()
            .pageNumber(page.getNumber())
            .pageSize(page.getSize())
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .first(page.isFirst())
            .last(page.isLast())
            .build();
    }

    @Builder
    @Data
    static class PageInfo {
        private int pageNumber;
        private int pageSize;
        private int totalPages;
        private long totalElements;
        private boolean first;
        private boolean last;
    }
}
