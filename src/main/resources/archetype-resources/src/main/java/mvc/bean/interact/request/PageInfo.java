package ${groupId}.mvc.bean.interact.request;


import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "每页数目，默认值10")
    private Integer pageSize = 10;


    @ApiModelProperty(value = "当前页，默认值1")
    private Integer currPage = 1;


}
