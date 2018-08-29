package ${groupId}.web.exception;

import ${groupId}.mvc.bean.interact.response.ErrorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyExceptionHandlers {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //普通参数校验错误,json参数校验错误
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Object validExceptionHandler(Exception e) {
        BindingResult result;
        if (e instanceof BindException) {
            result = ((BindException) e).getBindingResult();
        } else {
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach((fe) -> errors.put(fe.getField(), fe.getDefaultMessage()));
        return ResponseEntity.badRequest().body(ErrorResult.error(40000, errors));
    }


    //没session
    @ExceptionHandler({ServletRequestBindingException.class})
    public Object sessionMiss(ServletRequestBindingException e) {
        return ${groupId}.mvc.bean.Msg.authorityAndDebug("请先登录", e.getMessage());
    }

    //    业务错误
    @ExceptionHandler({${groupId}.web.exception.BusinessException.class})
    public Object handleBusinessException(${groupId}.web.exception.BusinessException e) {
        return new ResponseEntity<>(e.getError(), e.getHttpStatus());
    }

    //参数有误（如分页传进的参数）
    @ExceptionHandler({IllegalArgumentException.class})
    public Object handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(ErrorResult.error(40000, "参数有误：" + e.getMessage()));
    }

    //数字格式化错误
    @ExceptionHandler(NumberFormatException.class)
    public ${groupId}.mvc.bean.Msg numberFormatExceptionHandler(NumberFormatException e) {
        return ${groupId}.mvc.bean.Msg.failed(e.getLocalizedMessage());
    }

    //上传错误
    @ExceptionHandler(MultipartException.class)
    public void multipartExceptionHandler(MultipartException e) {
        log.error("发生上传错误", e);
    }


    //格式转化错误
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ${groupId}.mvc.bean.Msg converterError(HttpMessageNotReadableException e) {
        return ${groupId}.mvc.bean.Msg.failedAndDebug("参数有误", e.getMessage());
    }

    //上传限制错误
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
        log.error("发生上传限制错误", e);
    }


    @ExceptionHandler
    public Object handleException(Exception e) {
        log.error("发生未知错误：", e);
        return new ResponseEntity<>(new ErrorResult(50000, "未知错误： " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
