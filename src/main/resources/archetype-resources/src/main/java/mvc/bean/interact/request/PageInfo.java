package ${groupId}.mvc.bean.interact.request;


import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
public class PageInfo {

    public PageRequest getPageRequest() {
        return PageRequest.of(currPage - 1, pageSize);
    }

    public PageRequest getPageRequest(Sort.Direction direction, String... properties) {
        return PageRequest.of(currPage - 1, pageSize, direction, properties);
    }

    private Integer pageSize = 10;

    private Integer currPage = 1;


}
