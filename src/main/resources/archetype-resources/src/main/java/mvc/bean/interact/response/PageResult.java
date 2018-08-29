package ${groupId}.mvc.bean.interact.response;


import ${groupId}.mvc.bean.interact.request.PageInfo;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResult {
    public PageResult(Page page, PageInfo pageInfo) {
        this.pageData = page.getContent();
        this.totalPage = page.getTotalPages();
        this.pageSize = pageInfo.getPageSize();
        this.currPage = pageInfo.getCurrPage();
    }

    private List pageData;
    private Integer totalPage;
    private Integer pageSize;
    private Integer currPage;

}
