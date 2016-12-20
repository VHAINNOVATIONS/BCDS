package gov.va.vba.persistence.models.data;

import java.util.Date;

/**
 * Created by ProSphere User on 12/19/2016.
 */
public class KneeClaim {

    private long veteranId;
    private long claimId;
    private long contentionClassificationId;
    private Date profileDate;
    private Date claimDate;
    private String claimROName;
    private long claimRONumber;
    private String contentionClaimantText;

    public long getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(long veteranId) {
        this.veteranId = veteranId;
    }

    public long getClaimId() {
        return claimId;
    }

    public void setClaimId(long claimId) {
        this.claimId = claimId;
    }

    public long getContentionClassificationId() {
        return contentionClassificationId;
    }

    public void setContentionClassificationId(long contentionClassificationId) {
        this.contentionClassificationId = contentionClassificationId;
    }

    public Date getProfileDate() {
        return profileDate;
    }

    public void setProfileDate(Date profileDate) {
        this.profileDate = profileDate;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimROName() {
        return claimROName;
    }

    public void setClaimROName(String claimROName) {
        this.claimROName = claimROName;
    }

    public long getClaimRONumber() {
        return claimRONumber;
    }

    public void setClaimRONumber(long claimRONumber) {
        this.claimRONumber = claimRONumber;
    }

    public String getContentionClaimantText() {
        return contentionClaimantText;
    }

    public void setContentionClaimantText(String contentionClaimantText) {
        this.contentionClaimantText = contentionClaimantText;
    }

    @Override
    public String toString() {
        return "KneeClaim{" +
                "veteranId=" + veteranId +
                ", claimId=" + claimId +
                ", contentionClassificationId=" + contentionClassificationId +
                ", profileDate=" + profileDate +
                ", claimDate=" + claimDate +
                ", claimROName='" + claimROName + '\'' +
                ", claimRONumber=" + claimRONumber +
                ", contentionClaimantText='" + contentionClaimantText + '\'' +
                '}';
    }

}
