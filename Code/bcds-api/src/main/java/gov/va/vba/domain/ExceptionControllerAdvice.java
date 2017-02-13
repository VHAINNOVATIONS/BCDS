package gov.va.vba.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

	 @ExceptionHandler(CustomBCDSSException.class)
	    public ErrorResponse exceptionHandler(CustomBCDSSException e) {
	    	ErrorResponse error = new ErrorResponse();
	        error.setErrorCode(HttpStatus.SERVICE_UNAVAILABLE.value());
	        error.setMessage(e.getMessage());
	        return new ErrorResponse(e.getMessage());
	    }
}
