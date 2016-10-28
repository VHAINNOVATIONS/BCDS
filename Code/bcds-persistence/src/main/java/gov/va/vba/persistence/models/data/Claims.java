package main.java.gov.va.vba.persistence.models.data;

import java.io.Serializable;
import java.util.Set;
import java.lang.String;
import java.util.Date;

public class Claims implements Serializable {

	private long veteranId;
	private Date profileDate;
	private long claimId;
	private String endPrdctTypeCode;
	private Date claimDate;
	private String payeeTypeCode;
	private String claimTypeCode;
	private String claimLabel;
	private String statusTypeCode;
	private long claimRegionalOfficeNumber;
	private String regionalOfficeOfClaim;
	private String contentionId;
	private Long contentionClsfcnId;
	private String contentionTypeCode;
	private String contentionClaimTextKeyForModel;
	private String contentionMedInd;
	private String contentionWellGrndedAplcblInd;
	private Date contentionBeginDate;
	private String contentionSpeclIssueId;
	private String contentionSpeclIssueTypeCode;
	
	private Set<Veterans> veterans;

	public Claims() {
	}

	public Claims(long veteranId, Date profileDate, long claimId, Date claimDate,
			String claimTypeCode, String claimLabel, String statusTypeCode, long claimRegionalOfficeNumber,
			String regionalOfficeOfClaim) {
		this.veteranId = veteranId;
		this.profileDate = profileDate;
		this.claimId = claimId;
		this.claimDate = claimDate;
		this.claimTypeCode = claimTypeCode;
		this.claimLabel = claimLabel;
		this.statusTypeCode = statusTypeCode;
		this.claimRegionalOfficeNumber = claimRegionalOfficeNumber;
		this.regionalOfficeOfClaim = regionalOfficeOfClaim;
	}

	public Claims(long veteranId, Date profileDate, long claimId, String endPrdctTypeCode,
			Date claimDate, String payeeTypeCode, String claimTypeCode, String claimLabel, String statusTypeCode,
			long claimRegionalOfficeNumber, String regionalOfficeOfClaim, String contentionId, Long contentionClsfcnId, String contentionTypeCode,
			String contentionClaimTextKeyForModel, String contentionMedInd, String contentionWellGrndedAplcblInd, Date contentionBeginDate,
			String contentionSpeclIssueId, String contentionSpeclIssueTypeCode) {
		this.veteranId = veteranId;
		this.profileDate = profileDate;
		this.claimId = claimId;
		this.endPrdctTypeCode = endPrdctTypeCode;
		this.claimDate = claimDate;
		this.payeeTypeCode = payeeTypeCode;
		this.claimTypeCode = claimTypeCode;
		this.claimLabel = claimLabel;
		this.statusTypeCode = statusTypeCode;
		this.claimRegionalOfficeNumber = claimRegionalOfficeNumber;
		this.regionalOfficeOfClaim = regionalOfficeOfClaim;
		this.contentionId = contentionId;
		this.contentionClsfcnId = contentionClsfcnId;
		this.contentionTypeCode = contentionTypeCode;
		this.contentionClaimTextKeyForModel = contentionClaimTextKeyForModel;
		this.contentionMedInd = contentionMedInd;
		this.contentionWellGrndedAplcblInd = contentionWellGrndedAplcblInd;
		this.contentionBeginDate = contentionBeginDate;
		this.contentionSpeclIssueId = contentionSpeclIssueId;
		this.contentionSpeclIssueTypeCode = contentionSpeclIssueTypeCode;
	}

	public long getVeteranId() {
		return this.veteranId;
	}

	public void setVeteranId(long veteranId) {
		this.veteranId = veteranId;
	}

	public Date getPrfilDate() {
		return this.profileDate;
	}

	public void setPrfilDate(Date profileDate) {
		this.profileDate = profileDate;
	}

	public long getClaimId() {
		return this.claimId;
	}

	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}

	public String getEndPrdctTypeCode() {
		return this.endPrdctTypeCode;
	}

	public void setEndPrdctTypeCode(String endPrdctTypeCode) {
		this.endPrdctTypeCode = endPrdctTypeCode;
	}

	public Date getClaimDate() {
		return this.claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public String getPayeeTypeCode() {
		return this.payeeTypeCode;
	}

	public void setPayeeTypeCode(String payeeTypeCode) {
		this.payeeTypeCode = payeeTypeCode;
	}

	public String getClaimTypeCode() {
		return this.claimTypeCode;
	}

	public void setClaimTypeCode(String claimTypeCode) {
		this.claimTypeCode = claimTypeCode;
	}

	public String getClaimLabel() {
		return this.claimLabel;
	}

	public void setClaimLabel(String claimLabel) {
		this.claimLabel = claimLabel;
	}

	public String getStatusTypeCode() {
		return this.statusTypeCode;
	}

	public void setStatusTypeCode(String statusTypeCode) {
		this.statusTypeCode = statusTypeCode;
	}

	public long getClaimRegionalOfficeNumber() {
		return this.claimRegionalOfficeNumber;
	}

	public void setClaimRegionalOfficeNumber(long claimRegionalOfficeNumber) {
		this.claimRegionalOfficeNumber = claimRegionalOfficeNumber;
	}

	public String getClaimRoName() {
		return this.regionalOfficeOfClaim;
	}

	public void setClaimRoName(String regionalOfficeOfClaim) {
		this.regionalOfficeOfClaim = regionalOfficeOfClaim;
	}

	public String getContentionId() {
		return this.contentionId;
	}

	public void setContentionId(String contentionId) {
		this.contentionId = contentionId;
	}

	public Long getContentionClsfcnId() {
		return this.contentionClsfcnId;
	}

	public void setContentionClsfcnId(Long contentionClsfcnId) {
		this.contentionClsfcnId = contentionClsfcnId;
	}

	public String getContentionTypeCode() {
		return this.contentionTypeCode;
	}

	public void setContentionTypeCode(String contentionTypeCode) {
		this.contentionTypeCode = contentionTypeCode;
	}

	public String getContentionClmantTxt() {
		return this.contentionClaimTextKeyForModel;
	}

	public void setContentionClmantTxt(String contentionClaimTextKeyForModel) {
		this.contentionClaimTextKeyForModel = contentionClaimTextKeyForModel;
	}

	public String getContentionMedInd() {
		return this.contentionMedInd;
	}

	public void setContentionMedInd(String contentionMedInd) {
		this.contentionMedInd = contentionMedInd;
	}

	public String getContentionWellGrndedAplcblInd() {
		return this.contentionWellGrndedAplcblInd;
	}

	public void setContentionWellGrndedAplcblInd(String contentionWellGrndedAplcblInd) {
		this.contentionWellGrndedAplcblInd = contentionWellGrndedAplcblInd;
	}

	public Date getContentionBeginDate() {
		return this.contentionBeginDate;
	}

	public void setContentionBeginDate(Date contentionBeginDate) {
		this.contentionBeginDate = contentionBeginDate;
	}

	public String getContentionSpeclIssueId() {
		return this.contentionSpeclIssueId;
	}

	public void setContentionSpeclIssueId(String contentionSpeclIssueId) {
		this.contentionSpeclIssueId = contentionSpeclIssueId;
	}

	public String getContentionSpeclIssueTypeCode() {
		return this.contentionSpeclIssueTypeCode;
	}

	public void setContentionSpeclIssueTypeCode(String contentionSpeclIssueTypeCode) {
		this.contentionSpeclIssueTypeCode = contentionSpeclIssueTypeCode;
	}
	
	public Set<Veterans> getVeterans() {
		return veterans;
	}
	
	public void setVeterans(Set<Veterans> veterans) {
		this.veterans = veterans;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Claims))
			return false;
		Claims castOther = (Claims) other;

		return (this.getVeteranId() == castOther.getVeteranId())
				&& ((this.getPrfilDate() == castOther.getPrfilDate()) || (this.getPrfilDate() != null
						&& castOther.getPrfilDate() != null && this.getPrfilDate().equals(castOther.getPrfilDate())))
				&& (this.getClaimId() == castOther.getClaimId())
				&& ((this.getEndPrdctTypeCode() == castOther.getEndPrdctTypeCode())
						|| (this.getEndPrdctTypeCode() != null && castOther.getEndPrdctTypeCode() != null
								&& this.getEndPrdctTypeCode().equals(castOther.getEndPrdctTypeCode())))
				&& ((this.getClaimDate() == castOther.getClaimDate())
						|| (this.getClaimDate() != null && castOther.getClaimDate() != null
								&& this.getClaimDate().equals(castOther.getClaimDate())))
				&& ((this.getPayeeTypeCode() == castOther.getPayeeTypeCode())
						|| (this.getPayeeTypeCode() != null && castOther.getPayeeTypeCode() != null
								&& this.getPayeeTypeCode().equals(castOther.getPayeeTypeCode())))
				&& ((this.getClaimTypeCode() == castOther.getClaimTypeCode())
						|| (this.getClaimTypeCode() != null && castOther.getClaimTypeCode() != null
								&& this.getClaimTypeCode().equals(castOther.getClaimTypeCode())))
				&& ((this.getClaimLabel() == castOther.getClaimLabel()) || (this.getClaimLabel() != null
						&& castOther.getClaimLabel() != null && this.getClaimLabel().equals(castOther.getClaimLabel())))
				&& ((this.getStatusTypeCode() == castOther.getStatusTypeCode())
						|| (this.getStatusTypeCode() != null && castOther.getStatusTypeCode() != null
								&& this.getStatusTypeCode().equals(castOther.getStatusTypeCode())))
				&& ((this.getClaimRegionalOfficeNumber() == castOther.getClaimRegionalOfficeNumber())
						|| (this.getClaimRegionalOfficeNumber() != 0 && castOther.getClaimRegionalOfficeNumber() != 0)
								&& (this.getClaimRegionalOfficeNumber() == castOther.getClaimRegionalOfficeNumber()))
				&& ((this.getClaimRoName() == castOther.getClaimRoName())
						|| (this.getClaimRoName() != null && castOther.getClaimRoName() != null
								&& this.getClaimRoName().equals(castOther.getClaimRoName())))
				&& ((this.getContentionId() == castOther.getContentionId()) || (this.getContentionId() != null
						&& castOther.getContentionId() != null && this.getContentionId().equals(castOther.getContentionId())))
				&& ((this.getContentionClsfcnId() == castOther.getContentionClsfcnId())
						|| (this.getContentionClsfcnId() != null && castOther.getContentionClsfcnId() != null
								&& this.getContentionClsfcnId().equals(castOther.getContentionClsfcnId())))
				&& ((this.getContentionTypeCode() == castOther.getContentionTypeCode())
						|| (this.getContentionTypeCode() != null && castOther.getContentionTypeCode() != null
								&& this.getContentionTypeCode().equals(castOther.getContentionTypeCode())))
				&& ((this.getContentionClmantTxt() == castOther.getContentionClmantTxt())
						|| (this.getContentionClmantTxt() != null && castOther.getContentionClmantTxt() != null
								&& this.getContentionClmantTxt().equals(castOther.getContentionClmantTxt())))
				&& ((this.getContentionMedInd() == castOther.getContentionMedInd())
						|| (this.getContentionMedInd() != null && castOther.getContentionMedInd() != null
								&& this.getContentionMedInd().equals(castOther.getContentionMedInd())))
				&& ((this.getContentionWellGrndedAplcblInd() == castOther.getContentionWellGrndedAplcblInd())
						|| (this.getContentionWellGrndedAplcblInd() != null
								&& castOther.getContentionWellGrndedAplcblInd() != null
								&& this.getContentionWellGrndedAplcblInd()
										.equals(castOther.getContentionWellGrndedAplcblInd())))
				&& ((this.getContentionBeginDate() == castOther.getContentionBeginDate())
						|| (this.getContentionBeginDate() != null && castOther.getContentionBeginDate() != null
								&& this.getContentionBeginDate().equals(castOther.getContentionBeginDate())))
				&& ((this.getContentionSpeclIssueId() == castOther.getContentionSpeclIssueId())
						|| (this.getContentionSpeclIssueId() != null && castOther.getContentionSpeclIssueId() != null
								&& this.getContentionSpeclIssueId().equals(castOther.getContentionSpeclIssueId())))
				&& ((this.getContentionSpeclIssueTypeCode() == castOther.getContentionSpeclIssueTypeCode())
						|| (this.getContentionSpeclIssueTypeCode() != null && castOther.getContentionSpeclIssueTypeCode() != null
								&& this.getContentionSpeclIssueTypeCode().equals(castOther.getContentionSpeclIssueTypeCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getVeteranId();
		result = 37 * result + (getPrfilDate() == null ? 0 : this.getPrfilDate().hashCode());
		result = 37 * result + (int) this.getClaimId();
		result = 37 * result + (getEndPrdctTypeCode() == null ? 0 : this.getEndPrdctTypeCode().hashCode());
		result = 37 * result + (getClaimDate() == null ? 0 : this.getClaimDate().hashCode());
		result = 37 * result + (getPayeeTypeCode() == null ? 0 : this.getPayeeTypeCode().hashCode());
		result = 37 * result + (getClaimTypeCode() == null ? 0 : this.getClaimTypeCode().hashCode());
		result = 37 * result + (getClaimLabel() == null ? 0 : this.getClaimLabel().hashCode());
		result = 37 * result + (getStatusTypeCode() == null ? 0 : this.getStatusTypeCode().hashCode());
		result = 37 * result + (int) this.getClaimRegionalOfficeNumber();
		result = 37 * result + (getClaimRoName() == null ? 0 : this.getClaimRoName().hashCode());
		result = 37 * result + (getContentionId() == null ? 0 : this.getContentionId().hashCode());
		result = 37 * result + (getContentionClsfcnId() == null ? 0 : this.getContentionClsfcnId().hashCode());
		result = 37 * result + (getContentionTypeCode() == null ? 0 : this.getContentionTypeCode().hashCode());
		result = 37 * result + (getContentionClmantTxt() == null ? 0 : this.getContentionClmantTxt().hashCode());
		result = 37 * result + (getContentionMedInd() == null ? 0 : this.getContentionMedInd().hashCode());
		result = 37 * result + (getContentionWellGrndedAplcblInd() == null ? 0 : this.getContentionWellGrndedAplcblInd().hashCode());
		result = 37 * result + (getContentionBeginDate() == null ? 0 : this.getContentionBeginDate().hashCode());
		result = 37 * result + (getContentionSpeclIssueId() == null ? 0 : this.getContentionSpeclIssueId().hashCode());
		result = 37 * result + (getContentionSpeclIssueTypeCode() == null ? 0 : this.getContentionSpeclIssueTypeCode().hashCode());
		return result;
	}

}
