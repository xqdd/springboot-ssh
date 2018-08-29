package ${groupId}.web.exception;


import ${groupId}.mvc.bean.interact.response.ErrorResult;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {


    protected HttpStatus httpStatus;
    protected ErrorResult error;

    public BusinessException(ErrorResult error, HttpStatus httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ErrorResult getError() {
        return error;
    }

    public void setError(ErrorResult error) {
        this.error = error;
    }
}