package gov.va.vba.service.exception;

import gov.va.vba.service.common.Error;

/**
 * Created by ProSphere User on 3/7/2017.
 */
public class GenericException extends Exception {

    private static final String[] STRINGS = new String[]{" "};
    private Error error;

    public GenericException(Error error, String... args) {
        super(String.format(error.toString(), (args.length > 0) ? args : STRINGS));
        this.error = error;
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(Error error, Throwable cause, String... args) {
        super(String.format(error.toString(), (args.length > 0) ? args : STRINGS), cause);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    public Error getError() {
        return error;
    }
}
