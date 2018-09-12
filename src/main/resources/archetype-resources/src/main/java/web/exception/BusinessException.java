package ${groupId}.web.exception;


import ${groupId}.mvc.bean.interact.response.Result;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {


    protected HttpStatus httpStatus;
    protected Result error;

    public BusinessException(Result error, HttpStatus httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Result getError() {
        return error;
    }

    public void setError(Result error) {
        this.error = error;
    }
}