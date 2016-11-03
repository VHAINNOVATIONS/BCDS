package gov.va.vba.domain.util;

import java.io.Serializable;

/**
 * Created by ProSphere User on 11/3/2016.
 */
public class Veteran implements Serializable{

    private Long veteranId;
    private String BirthYear;
    private String veteranGender;
    private String dateOfDec;
    private String stateCd;

    public Long getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Long veteranId) {
        this.veteranId = veteranId;
    }

    public String getBirthYear() {
        return BirthYear;
    }

    public void setBirthYear(String birthYear) {
        BirthYear = birthYear;
    }

    public String getVeteranGender() {
        return veteranGender;
    }

    public void setVeteranGender(String veteranGender) {
        this.veteranGender = veteranGender;
    }

    public String getDateOfDec() {
        return dateOfDec;
    }

    public void setDateOfDec(String dateOfDec) {
        this.dateOfDec = dateOfDec;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }
}
