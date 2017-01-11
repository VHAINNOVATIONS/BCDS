package gov.va.vba.persistence.models.data;

import java.util.Date;

/**
 * Created by ProSphere User on 12/19/2016.
 */
public class ClaimDetails {

    private long veteranId;
    private long claimId;
    private long contentionClassificationId;
    private Date profileDate;
    private Date claimDate;
    private String claimROName;
    private long claimRONumber;
    private long contentionId;
    private String contentionClaimantText;
    private Date cestDate;

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

    public Long getContentionId() {
		return contentionId;
	}
	public void setContentionId(Long contentionId) {
		this.contentionId = contentionId;
	}
	
    public String getContentionClaimantText() {
        return contentionClaimantText;
    }

    public void setContentionClaimantText(String contentionClaimantText) {
        this.contentionClaimantText = contentionClaimantText;
    }

    public Date getCestDate() {
        return cestDate;
    }

    public void setCestDate(Date cestDate) {
        this.cestDate = cestDate;
    }

    @Override
    public String toString() {
        return "ClaimDetails{" +
                "veteranId=" + veteranId +
                ", claimId=" + claimId +
                ", contentionClassificationId=" + contentionClassificationId +
                ", profileDate=" + profileDate +
                ", claimDate=" + claimDate +
                ", claimROName='" + claimROName + '\'' +
                ", claimRONumber=" + claimRONumber +
                ", contentionId=" + contentionId +
                ", contentionClaimantText='" + contentionClaimantText + '\'' +
                ", cestDate=" + cestDate +
                '}';
    }

}
