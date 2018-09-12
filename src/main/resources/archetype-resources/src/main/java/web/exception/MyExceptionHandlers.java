package ${groupId}.web.exception;

import com.alibaba.fastjson.JSONException;
import ${groupId}.mvc.bean.interact.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class MyExceptionHandlers {


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
        return Result.error(40000, errors);
    }


    //没session
    @ExceptionHandler({ServletRequestBindingException.class})
    public Object sessionMiss(ServletRequestBindingException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Result.error("请先登录：" + e.getMessage()));
    }

    //业务错误
    @ExceptionHandler({BusinessException.class})
    public Object handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(e.getError());
    }


    @ExceptionHandler({Throwable.class})
    public Object otherException(Throwable e) {
        log.error("未知错误", e);
        return Result.error(50000, "服务器发生未知错误：" + e.getMessage());
    }


    //参数转化错误
    @ExceptionHandler({
            NumberFormatException.class,
            IllegalStateException.class,
            IllegalArgumentException.class,
            JSONException.class,
    })
    public Object numberError(Throwable t) {
        return Result.error(40000, t.getMessage());
    }

}
