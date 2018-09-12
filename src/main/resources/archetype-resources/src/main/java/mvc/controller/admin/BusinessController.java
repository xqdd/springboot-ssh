package ${groupId}.mvc.controller.admin;


import com.fasterxml.jackson.annotation.JsonView;
import ${groupId}.mvc.bean.entity.Business;
import ${groupId}.mvc.bean.interact.request.PageInfo;
import ${groupId}.mvc.bean.interact.response.Result;
import ${groupId}.mvc.service.BusinessService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RestController("adminBusinessController")
@RequestMapping(value = "admin/business", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "商家")
public class BusinessController {


    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }


    @GetMapping("getBusiness")
    @ApiOperation("获取商家列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字"),
            @ApiImplicitParam(name = "status", value = "状态，不传:全部,0待审核,1通过,2失败,3已删除"),
    })
    @JsonView(Business.VO.AdminList.class)
    public Object getBusiness(PageInfo pageInfo, String keyword, Integer status) {
        return Result.success(businessService.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(keyword)) {
                predicates.add(cb.or(cb.like(root.get("phoneNumber"), "%" + keyword + "%")
                        , cb.like(root.get("companyName"), "%" + keyword + "%")));
            }
            if (status != null) {
                if (status == 0) {
                    predicates.add(cb.or(cb.equal(root.get("status"), 0), cb.equal(root.get("status"), 3)));
                } else if (status == 1 || status == 2) {
                    predicates.add(cb.equal(root.get("status"), status));
                } else if (status == 3) {
                    predicates.add(cb.equal(root.get("deleted"), true));
                }
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        }, pageInfo.getPageRequest()));

    }


    @GetMapping("getBusinessDetail")
    @ApiOperation("获取商家详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商家id", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "code："
                    + "<br/>40001：该商家不存在"
            )
    })
    @JsonView(Business.VO.AdminList.class)
    public Object getBusinessDetail(int id) {
        return businessService.findById(id)
                .<Object>map(Result::success)
                .orElseGet(() -> Result.error(40001, "该商家不存在"));
    }


    @PostMapping("delete")
    @ApiOperation("删除商家")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商家id", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "code："
                    + "<br/>40001：该商家不存在"
                    + "<br/>40002：该商家已被删除"
            )
    })
    public Object delete(int id) {
        var optionalBusiness = businessService.findById(id);
        if (optionalBusiness.isPresent()) {
            Business business = optionalBusiness.get();
            if (business.isDeleted()) {
                return Result.error(40002, "该商家已被删除");
            }
            business.setDeleted(true);
            businessService.save(business);
            return Result.success("操作成功");
        } else {
            return Result.error(40001, "该商家不存在");
        }
    }


    @PostMapping("restore")
    @ApiOperation("恢复商家")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商家id", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "code："
                    + "<br/>40001：该商家不存在"
                    + "<br/>40002：该商家未被删除"
            )
    })
    public Object restore(int id) {
        var optionalBusiness = businessService.findById(id);
        if (optionalBusiness.isPresent()) {
            Business business = optionalBusiness.get();
            if (!business.isDeleted()) {
                return Result.error(40002, "该商家未被删除");
            }
            business.setDeleted(false);
            businessService.save(business);
            return Result.success("操作成功");
        } else {
            return Result.error(40001, "该商家不存在");
        }
    }


    @PostMapping("passCheck")
    @ApiOperation("审核通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商家id", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "code："
                    + "<br/>40001：该商家不存在"
                    + "<br/>40002：该商家已被审核过"
            )
    })
    public Object passCheck(int id) {
        var optionalBusiness = businessService.findById(id);
        if (optionalBusiness.isPresent()) {
            Business business = optionalBusiness.get();
            if (business.getStatus() != 0) {
                return Result.error(40002, "该商家已被审核过");
            }
            business.setStatus(1);
            businessService.save(business);
            return Result.success("操作成功");
        } else {
            return Result.error(40001, "该商家不存在");
        }
    }


    @PostMapping("notPassCheck")
    @ApiOperation("审核不通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商家id", required = true),
            @ApiImplicitParam(name = "reason", value = "不通过审核理由"),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "code："
                    + "<br/>40001：该商家不存在"
                    + "<br/>40002：该商家已被审核过"
            )
    })
    public Object notPassCheck(int id, String reason) {
        var optionalBusiness = businessService.findById(id);
        if (optionalBusiness.isPresent()) {
            Business business = optionalBusiness.get();
            if (business.getStatus() != 0) {
                return Result.error(40002, "该商家已被审核过");
            }
            business.setStatus(2);
            business.setCheckFailedReason(reason);
            businessService.save(business);
            return Result.success("操作成功");
        } else {
            return Result.error(40001, "该商家不存在");
        }
    }


}
