package gov.va.vba.service.common;

/**
 * Created by ProSphere User on 3/7/2017.
 */
public enum Error {

    ER_1001("Claims are not found for the veteran id %s and claim id %s"),
    ER_1002("No previous decisions are available for the veteran id %s and claim id %s"),
    ER_1003("There are no contentions or diagnosis found for the veteran id %s and claim date %s"),
    ER_1004("This service is currently unavailable. Please try after some time");

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
