package ${groupId}.web.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class MyExceptionHandlers {

    @ExceptionHandler(BindException.class)
    public Object validExceptionHandler(BindException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fes = result.getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        fes.forEach((fe) -> {
            errors.put(fe.getField(), fe.getDefaultMessage());
        });
        return new ${groupId}.bean.Msg(${groupId}.bean.Msg.CODE_FAILED, errors);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ${groupId}.bean.Msg numberFormatExceptionHandler(NumberFormatException e) {
        return ${groupId}.bean.Msg.failed("msg", e.getLocalizedMessage());
    }


    @ExceptionHandler(MultipartException.class)
    public void multipartExceptionHandler() {

        System.out.println("okk");

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ${groupId}.bean.Msg maxUploadSizeExceededExceptionHandler() {
        System.out.println("okk22222222222");
        return ${groupId}.bean.Msg.failed("img", "图片大小不得大于20MB");
    }
}
