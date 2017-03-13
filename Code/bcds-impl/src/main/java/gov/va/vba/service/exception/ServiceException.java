package gov.va.vba.service.exception;

import gov.va.vba.service.common.Error;

/**
 * Created by ProSphere User on 3/7/2017.
 */
public class ServiceException extends GenericException {

    public ServiceException(Error error, String... args) {
        super(error, args);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Error error, Throwable cause, String... args) {
        super(error, cause, args);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
