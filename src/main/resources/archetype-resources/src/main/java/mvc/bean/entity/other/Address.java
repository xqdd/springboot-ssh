package ${groupId}.mvc.bean.entity.other;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;
    @ApiModelProperty(value = "县区",required = true)
    @NotBlank
    private String area;
    @ApiModelProperty(value = "省份",required = true)
    @NotBlank
    private String province;
    @ApiModelProperty(value = "城市",required = true)
    @NotBlank
    private String city;


}
