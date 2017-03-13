package gov.va.vba.service.common;

/**
 * Created by ProSphere User on 3/7/2017.
 */
public enum Error {

    ER_1001("No records found for the veteran id %s and claim id %s"),
    ER_1002("Did not find previous rating decisions for the selected veteran id %s and claim id %s"),
    ER_1003("This service is temporarily unavailable. Please try after some time");

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
