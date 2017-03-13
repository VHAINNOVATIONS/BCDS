package gov.va.vba.web.rest.exception;

/**
 * Created by ProSphere User on 3/7/2017.
 */
public class ExceptionResponse {

    private String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
