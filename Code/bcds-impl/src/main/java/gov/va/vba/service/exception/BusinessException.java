package gov.va.vba.service.exception;

import gov.va.vba.service.common.Error;

/**
 * Created by ProSphere User on 3/7/2017.
 */
public class BusinessException extends GenericException {

    public BusinessException(Error error, String... args) {
        super(error, args);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Error error, Throwable cause, String... args) {
        super(error, cause, args);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

}
