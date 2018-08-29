package ${groupId}.mvc.controller;

import ${groupId}.mvc.bean.interact.response.ErrorResult;
import ${groupId}.mvc.bean.interact.response.MessageResponse;
import ${groupId}.mvc.bean.interact.response.SuccessResult;
import ${groupId}.utils.CommUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "comm", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "通用")
public class CommController {


    private final RedisTemplate redisTemplate;
    private final HttpServletRequest request;


    @Autowired
    public CommController(RedisTemplate redisTemplate, HttpServletRequest request) {
        this.redisTemplate = redisTemplate;
        this.request = request;
    }

    private static final String vCodeTypes = "phone_login,wx_register,phone_change_old,phone_change_new";
    private String[] vCodeType = vCodeTypes.split(",");

    @GetMapping("getPhoneVCode")
    @ApiOperation(value = "发送手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNumber", value = "手机号码", required = true),
            @ApiImplicitParam(name = "type",
                    value = "手机验证码类型："
                            + "<br/>phone_login：手机登录使用"
                            + "<br/>wx_register：微信登录注册使用"
                            + "<br/>phone_change_old：更换手机_校验旧手机使用"
                            + "<br/>phone_change_new：更换手机_校验新手机使用"
                    , required = true,
                    allowableValues = vCodeTypes),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{success:true}"),
            @ApiResponse(code = 400, message = "error_no："
                    + "<br/>40000：校验错误"
                    + "<br/>40001：短信1分钟限制"
                    + "<br/>40002：手机号码短信1天限制20条"
                    + "<br/>40003：手机空号等原因"
            ),
            @ApiResponse(code = 503, message = "error_no："
                    + "<br/>50300：服务器网络等原因发送失败"
                    + "<br/>50301：短信条数不足等原因"),
    })
    public ResponseEntity getPhoneVCode(String phoneNumber, String type) {
        //字段校验
        //1.校验验证码类型
        if (!ArrayUtils.contains(vCodeType, type)) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResult.errors(40000, "type", "格式不正确"));
        }
        //2.检查手机号码格式
        String regexp = "^1[0-9]{10}$";
        if (!Pattern.matches(regexp, phoneNumber)) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResult.errors(40000, "phoneNumber", "格式不正确"));
        }

        ValueOperations valueOperations = redisTemplate.opsForValue();
        //检查记录发送周期
        //1.1分钟限制
        if (valueOperations.get("send1MVCodeLimit_" + phoneNumber) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResult.errors(40001, "phoneNumber", "短信发送过于频繁"));
        }
        //2.手机号每天20条限制
        String send1DVCodeLimit = (String) valueOperations.get("send1DVCodeLimit_" + phoneNumber);
        if (send1DVCodeLimit != null && Integer.parseInt(send1DVCodeLimit) >= 20) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResult.errors(40002, "phoneNumber", "今天发送短信验证码次数超过上限"));
        }

        String vCode = CommUtils.randomNumber(4);
        MessageResponse response = CommUtils.sendPhoneVCode(phoneNumber, vCode);
        //服务器网络等原因发送失败
        if (response == null) {
            return new ResponseEntity<>
                    (ErrorResult.error(50300, "发送短信验证码失败，请稍后再试")
                            , HttpStatus.SERVICE_UNAVAILABLE);
        }
        //发送成功
        if (response.getStatus() == 0) {
            //保存短信验证码到redis中
            //验证码
            //TODO 5分钟限制
            valueOperations.set("vCode_" + type + "_" + phoneNumber, vCode, 5, TimeUnit.DAYS);
            //一分钟限制
            valueOperations.set("send1MVCodeLimit_" + phoneNumber, "", 1, TimeUnit.MINUTES);
            //一天限制
            if (send1DVCodeLimit != null) {
                send1DVCodeLimit = Integer.parseInt(send1DVCodeLimit) + 1 + "";
            } else {
                send1DVCodeLimit = "1";
            }
            valueOperations.set("send1DVCodeLimit_" + phoneNumber
                    , send1DVCodeLimit
                    , 1440 - CommUtils.calendar.get(Calendar.MINUTE) - 60 * CommUtils.calendar.get(Calendar.HOUR_OF_DAY)
                    , TimeUnit.MINUTES);
            return ResponseEntity.ok(SuccessResult.success());
        }
        //空号等原因
        else if (response.getStatus() == 201 || response.getStatus() == 202 || response.getStatus() == 220) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResult.errors(40003, "phoneNumber", "发送短信验证码失败，请检查手机号是否有误"));
        }
        //指定时间内发送数量超限
        else if (response.getStatus() == 210) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResult.errors(40001, "phoneNumber", "短信发送过于频繁"));
        }
        //短信条数不足等原因
        else {
            return new ResponseEntity<>
                    (ErrorResult.error(50301, "发送短信验证码失败，请稍后再试")
                            , HttpStatus.SERVICE_UNAVAILABLE);
        }

    }


    //图片上传
    @ApiOperation(value = "图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", required = true),
            @ApiImplicitParam(name = "img", value = "图片", paramType = "form", dataType = "__File")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{data:图片相对路径}"),
            @ApiResponse(code = 400, message = "error_no："
                    + "<br/>40000：图片为空"
                    + "<br/>40001：图片上传失败")
    })
    @PostMapping(value = "imageUpload", produces = "application/json; charset=utf-8")
    public Object imageUpload(MultipartFile img) {
        if (img == null || img.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResult.error(40000, "图片为空"));
        }
        String type = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
        String relativePath = "/temp/" + CommUtils.DATE_FORMAT.format(new Date()) + "/" + CommUtils.randomString(32, CommUtils.RandomType.ALL) + type;
        //TODO 路径加上..
//        File tempFile = new File(request.getServletContext().getRealPath("/") + relativePath);
//        File tempFile = new File("D:/environment/Apache24/htdocs/static/skill_exchange/" + relativePath);
        File tempFile = new File("/root/environment/apache-tomcat-9.0.10/webapps/static/skill_exchange/" + relativePath);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            img.transferTo(tempFile);
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResult.error(40001, "图片上传失败"));
        }
        return SuccessResult.success(relativePath);
    }

}
