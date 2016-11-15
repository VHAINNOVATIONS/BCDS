package gov.va.vba.web.rest.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ProSphere User on 11/4/2016.
 */
@Component
public class ClaimDTO implements Serializable {

    private boolean establishedDate;
    private Date fromDate;
    private Date toDate;
    private String contentionType;
    private Long regionalOfficeNumber;

    public boolean isEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(boolean establishedDate) {
        this.establishedDate = establishedDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getContentionType() {
        return contentionType;
    }

    public void setContentionType(String contentionType) {
        this.contentionType = contentionType;
    }

    public Long getRegionalOfficeNumber() {
        return regionalOfficeNumber;
    }

    public void setRegionalOfficeNumber(Long regionalOfficeNumber) {
        this.regionalOfficeNumber = regionalOfficeNumber;
    }

}
