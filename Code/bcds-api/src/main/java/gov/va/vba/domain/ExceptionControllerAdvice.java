/*package gov.va.vba.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

	 @ExceptionHandler(Exception.class)
	    public ErrorResponse exceptionHandler(Exception e) {
	    	ErrorResponse error = new ErrorResponse();
	        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
	        error.setMessage(e.getMessage());
	        return new ErrorResponse(e.getMessage());
	    }
}
*/