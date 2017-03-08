package gov.va.vba.service.common;

/**
 * Created by ProSphere User on 3/7/2017.
 */
public enum Error {

    ER_1001("Claims not found for veteran id %s and claim id %s"),
    ER_1002("Decisions not found");

    private String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return name();
    }

    @Override
    public String toString() {
        return getCode() + " :: " + getMessage();
    }
}
