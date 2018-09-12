package ${groupId}.mvc.bean.interact.response;


import com.fasterxml.jackson.annotation.JsonView;
import ${groupId}.utils.jsonview.CommJsonView;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@JsonView(CommJsonView.class)
class PageResult {
    PageResult(Page page) {
        this.content = page.getContent();
        this.pageSize = page.getSize();
        this.currPage = page.getNumber() + 1;
        this.totalPages = page.getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.totalElements = page.getTotalElements();
        this.currElements = page.getNumberOfElements();
        this.offset = page.getPageable().getOffset();
    }

    private List content;
    private long totalElements;
    private long offset;
    private int totalPages;
    private int pageSize;
    private int currPage;
    private int currElements;
    private boolean first;
    private boolean last;

}
