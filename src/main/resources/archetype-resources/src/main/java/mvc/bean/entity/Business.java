package ${groupId}.mvc.bean.entity;

import com.fasterxml.jackson.annotation.JsonView;
import ${groupId}.mvc.bean.entity.other.Address;
import ${groupId}.mvc.bean.entity.other.Image;
import ${groupId}.utils.jsonview.CommJsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(VO.Comm.class)
    private Integer id;

    @OneToOne(optional = false)
    @JsonView(VO.AdminDetail.class)
    private User user;

    @NotBlank
    @JsonView(VO.AdminComm.class)
    private String trueName;
    //身份证号
    @NotBlank
    @Length(min = 18, max = 18)
    @JsonView(VO.AdminComm.class)
    private String idCardNumber;
    //营业执照
    @NotBlank
    @JsonView(VO.AdminDetail.class)
    private String businessLicense;
    //入驻地点
    @OneToOne(cascade = CascadeType.ALL)
    @JsonView({VO.MiniAppDetail.class, VO.AdminComm.class})
    private Address address;
    @NotBlank
    @JsonView({VO.MiniAppDetail.class, VO.AdminComm.class})
    private String phoneNumber;
    //简介
    @NotBlank
    @JsonView({VO.MiniAppDetail.class, VO.AdminComm.class})
    private String briefIfo;
    //详情
    @NotBlank
    @JsonView({VO.MiniAppComm.class})
    private String detailInfo;
    //公司名
    @NotBlank
    @JsonView({VO.Comm.class})
    private String companyName;
    @NotBlank
    @JsonView({VO.Comm.class})
    private String companyIcon;

    @ApiModelProperty(hidden = true)
    @JsonView(VO.AdminDetail.class)
    private String checkFailedReason;
    @ApiModelProperty(hidden = true)
    @JsonView(VO.AdminDetail.class)
    private long requestTime;
    @ApiModelProperty(hidden = true)
    @JsonView(VO.AdminDetail.class)
    private Long enterTime;
    //优先级
    @Column(columnDefinition = "int(11) default 0")
    @Min(0)
    @Max(100)
    @ApiModelProperty(hidden = true)
    @JsonView(VO.AdminDetail.class)
    private int priority;

    //班次和价格
//    @OneToMany
//    @JsonView({VO.MiniAppDetail.class, VO.AdminDetail.class})
//    @Valid
//    private Set<Class> classes;

    //轮播图
    @OneToMany
    @JsonView({VO.MiniAppDetail.class, VO.AdminDetail.class})
    @Valid
    private Set<Image> images;

    //审核状态 0审核中 1审核通过 2审核失败 3重新审核中 4已删除
    @ApiModelProperty(hidden = true)
    @JsonView(VO.AdminComm.class)
    private int status;


    @Column(columnDefinition = "tinyint(1) default 0")
    @JsonView(VO.AdminComm.class)
    private boolean deleted;

    public interface VO {

        interface Comm extends CommJsonView {
        }

        interface MiniAppComm extends Comm {
        }

        interface MiniAppList extends MiniAppComm {
        }

        interface MiniAppDetail extends MiniAppComm {
        }

        interface AdminComm extends Comm {
        }

        interface AdminList extends AdminComm {
        }

        interface AdminDetail extends AdminComm {
        }
    }

}
