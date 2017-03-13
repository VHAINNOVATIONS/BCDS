package gov.va.vba.web.rest.exception;

/**
 * Created by ProSphere User on 3/7/2017.
 */
public class ProcessException extends Exception {

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }

}
