package gov.va.vba.web.rest.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ProSphere User on 3/7/2017.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProcessException.class)
    @ResponseBody
    public ExceptionResponse handleProcessException(ProcessException e) {
        return createResponse(e);
    }

    private ExceptionResponse createResponse(Exception e) {
        return new ExceptionResponse(e.getMessage());
    }

}
