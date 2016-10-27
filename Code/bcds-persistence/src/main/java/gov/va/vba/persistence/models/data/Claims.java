package main.java.gov.va.vba.persistence.models.data;

import java.lang.String;
import java.util.Date;

public class Claims {

	private long ptcpntVetId;
	private Date prfilDt;
	private long bnftClaimId;
	private String endPrdctTypeCd;
	private Date dateOfClaim;
	private String payeeTypeCd;
	private String bnftClaimTypeCd;
	private String claimLabel;
	private String statusTypeCd;
	private long claimRoNumber;
	private String claimRoName;
	private String cntntnId;
	private Long cntntnClsfcnId;
	private String cntntnTypeCd;
	private String cntntnClmantTxt;
	private String cntntnMedInd;
	private String cntntnWellGrndedAplcblInd;
	private Date cntntnBeginDt;
	private String cntntnSpeclIssueId;
	private String cntntnSpeclIssueTypeCd;

	public Claims() {
	}

	public Claims(long ptcpntVetId, Date prfilDt, long bnftClaimId, Date dateOfClaim,
			String bnftClaimTypeCd, String claimLabel, String statusTypeCd, long claimRoNumber,
			String claimRoName) {
		this.ptcpntVetId = ptcpntVetId;
		this.prfilDt = prfilDt;
		this.bnftClaimId = bnftClaimId;
		this.dateOfClaim = dateOfClaim;
		this.bnftClaimTypeCd = bnftClaimTypeCd;
		this.claimLabel = claimLabel;
		this.statusTypeCd = statusTypeCd;
		this.claimRoNumber = claimRoNumber;
		this.claimRoName = claimRoName;
	}

	public Claims(long ptcpntVetId, Date prfilDt, long bnftClaimId, String endPrdctTypeCd,
			Date dateOfClaim, String payeeTypeCd, String bnftClaimTypeCd, String claimLabel, String statusTypeCd,
			long claimRoNumber, String claimRoName, String cntntnId, Long cntntnClsfcnId, String cntntnTypeCd,
			String cntntnClmantTxt, String cntntnMedInd, String cntntnWellGrndedAplcblInd, Date cntntnBeginDt,
			String cntntnSpeclIssueId, String cntntnSpeclIssueTypeCd) {
		this.ptcpntVetId = ptcpntVetId;
		this.prfilDt = prfilDt;
		this.bnftClaimId = bnftClaimId;
		this.endPrdctTypeCd = endPrdctTypeCd;
		this.dateOfClaim = dateOfClaim;
		this.payeeTypeCd = payeeTypeCd;
		this.bnftClaimTypeCd = bnftClaimTypeCd;
		this.claimLabel = claimLabel;
		this.statusTypeCd = statusTypeCd;
		this.claimRoNumber = claimRoNumber;
		this.claimRoName = claimRoName;
		this.cntntnId = cntntnId;
		this.cntntnClsfcnId = cntntnClsfcnId;
		this.cntntnTypeCd = cntntnTypeCd;
		this.cntntnClmantTxt = cntntnClmantTxt;
		this.cntntnMedInd = cntntnMedInd;
		this.cntntnWellGrndedAplcblInd = cntntnWellGrndedAplcblInd;
		this.cntntnBeginDt = cntntnBeginDt;
		this.cntntnSpeclIssueId = cntntnSpeclIssueId;
		this.cntntnSpeclIssueTypeCd = cntntnSpeclIssueTypeCd;
	}

	public long getPtcpntVetId() {
		return this.ptcpntVetId;
	}

	public void setPtcpntVetId(long ptcpntVetId) {
		this.ptcpntVetId = ptcpntVetId;
	}

	public Date getPrfilDt() {
		return this.prfilDt;
	}

	public void setPrfilDt(Date prfilDt) {
		this.prfilDt = prfilDt;
	}

	public long getBnftClaimId() {
		return this.bnftClaimId;
	}

	public void setBnftClaimId(long bnftClaimId) {
		this.bnftClaimId = bnftClaimId;
	}

	public String getEndPrdctTypeCd() {
		return this.endPrdctTypeCd;
	}

	public void setEndPrdctTypeCd(String endPrdctTypeCd) {
		this.endPrdctTypeCd = endPrdctTypeCd;
	}

	public Date getDateOfClaim() {
		return this.dateOfClaim;
	}

	public void setDateOfClaim(Date dateOfClaim) {
		this.dateOfClaim = dateOfClaim;
	}

	public String getPayeeTypeCd() {
		return this.payeeTypeCd;
	}

	public void setPayeeTypeCd(String payeeTypeCd) {
		this.payeeTypeCd = payeeTypeCd;
	}

	public String getBnftClaimTypeCd() {
		return this.bnftClaimTypeCd;
	}

	public void setBnftClaimTypeCd(String bnftClaimTypeCd) {
		this.bnftClaimTypeCd = bnftClaimTypeCd;
	}

	public String getClaimLabel() {
		return this.claimLabel;
	}

	public void setClaimLabel(String claimLabel) {
		this.claimLabel = claimLabel;
	}

	public String getStatusTypeCd() {
		return this.statusTypeCd;
	}

	public void setStatusTypeCd(String statusTypeCd) {
		this.statusTypeCd = statusTypeCd;
	}

	public long getClaimRoNumber() {
		return this.claimRoNumber;
	}

	public void setClaimRoNumber(long claimRoNumber) {
		this.claimRoNumber = claimRoNumber;
	}

	public String getClaimRoName() {
		return this.claimRoName;
	}

	public void setClaimRoName(String claimRoName) {
		this.claimRoName = claimRoName;
	}

	public String getCntntnId() {
		return this.cntntnId;
	}

	public void setCntntnId(String cntntnId) {
		this.cntntnId = cntntnId;
	}

	public Long getCntntnClsfcnId() {
		return this.cntntnClsfcnId;
	}

	public void setCntntnClsfcnId(Long cntntnClsfcnId) {
		this.cntntnClsfcnId = cntntnClsfcnId;
	}

	public String getCntntnTypeCd() {
		return this.cntntnTypeCd;
	}

	public void setCntntnTypeCd(String cntntnTypeCd) {
		this.cntntnTypeCd = cntntnTypeCd;
	}

	public String getCntntnClmantTxt() {
		return this.cntntnClmantTxt;
	}

	public void setCntntnClmantTxt(String cntntnClmantTxt) {
		this.cntntnClmantTxt = cntntnClmantTxt;
	}

	public String getCntntnMedInd() {
		return this.cntntnMedInd;
	}

	public void setCntntnMedInd(String cntntnMedInd) {
		this.cntntnMedInd = cntntnMedInd;
	}

	public String getCntntnWellGrndedAplcblInd() {
		return this.cntntnWellGrndedAplcblInd;
	}

	public void setCntntnWellGrndedAplcblInd(String cntntnWellGrndedAplcblInd) {
		this.cntntnWellGrndedAplcblInd = cntntnWellGrndedAplcblInd;
	}

	public Date getCntntnBeginDt() {
		return this.cntntnBeginDt;
	}

	public void setCntntnBeginDt(Date cntntnBeginDt) {
		this.cntntnBeginDt = cntntnBeginDt;
	}

	public String getCntntnSpeclIssueId() {
		return this.cntntnSpeclIssueId;
	}

	public void setCntntnSpeclIssueId(String cntntnSpeclIssueId) {
		this.cntntnSpeclIssueId = cntntnSpeclIssueId;
	}

	public String getCntntnSpeclIssueTypeCd() {
		return this.cntntnSpeclIssueTypeCd;
	}

	public void setCntntnSpeclIssueTypeCd(String cntntnSpeclIssueTypeCd) {
		this.cntntnSpeclIssueTypeCd = cntntnSpeclIssueTypeCd;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Claims))
			return false;
		Claims castOther = (Claims) other;

		return (this.getPtcpntVetId() == castOther.getPtcpntVetId())
				&& ((this.getPrfilDt() == castOther.getPrfilDt()) || (this.getPrfilDt() != null
						&& castOther.getPrfilDt() != null && this.getPrfilDt().equals(castOther.getPrfilDt())))
				&& (this.getBnftClaimId() == castOther.getBnftClaimId())
				&& ((this.getEndPrdctTypeCd() == castOther.getEndPrdctTypeCd())
						|| (this.getEndPrdctTypeCd() != null && castOther.getEndPrdctTypeCd() != null
								&& this.getEndPrdctTypeCd().equals(castOther.getEndPrdctTypeCd())))
				&& ((this.getDateOfClaim() == castOther.getDateOfClaim())
						|| (this.getDateOfClaim() != null && castOther.getDateOfClaim() != null
								&& this.getDateOfClaim().equals(castOther.getDateOfClaim())))
				&& ((this.getPayeeTypeCd() == castOther.getPayeeTypeCd())
						|| (this.getPayeeTypeCd() != null && castOther.getPayeeTypeCd() != null
								&& this.getPayeeTypeCd().equals(castOther.getPayeeTypeCd())))
				&& ((this.getBnftClaimTypeCd() == castOther.getBnftClaimTypeCd())
						|| (this.getBnftClaimTypeCd() != null && castOther.getBnftClaimTypeCd() != null
								&& this.getBnftClaimTypeCd().equals(castOther.getBnftClaimTypeCd())))
				&& ((this.getClaimLabel() == castOther.getClaimLabel()) || (this.getClaimLabel() != null
						&& castOther.getClaimLabel() != null && this.getClaimLabel().equals(castOther.getClaimLabel())))
				&& ((this.getStatusTypeCd() == castOther.getStatusTypeCd())
						|| (this.getStatusTypeCd() != null && castOther.getStatusTypeCd() != null
								&& this.getStatusTypeCd().equals(castOther.getStatusTypeCd())))
				&& ((this.getClaimRoNumber() == castOther.getClaimRoNumber())
						|| (this.getClaimRoNumber() != 0 && castOther.getClaimRoNumber() != 0)
								&& (this.getClaimRoNumber() == castOther.getClaimRoNumber()))
				&& ((this.getClaimRoName() == castOther.getClaimRoName())
						|| (this.getClaimRoName() != null && castOther.getClaimRoName() != null
								&& this.getClaimRoName().equals(castOther.getClaimRoName())))
				&& ((this.getCntntnId() == castOther.getCntntnId()) || (this.getCntntnId() != null
						&& castOther.getCntntnId() != null && this.getCntntnId().equals(castOther.getCntntnId())))
				&& ((this.getCntntnClsfcnId() == castOther.getCntntnClsfcnId())
						|| (this.getCntntnClsfcnId() != null && castOther.getCntntnClsfcnId() != null
								&& this.getCntntnClsfcnId().equals(castOther.getCntntnClsfcnId())))
				&& ((this.getCntntnTypeCd() == castOther.getCntntnTypeCd())
						|| (this.getCntntnTypeCd() != null && castOther.getCntntnTypeCd() != null
								&& this.getCntntnTypeCd().equals(castOther.getCntntnTypeCd())))
				&& ((this.getCntntnClmantTxt() == castOther.getCntntnClmantTxt())
						|| (this.getCntntnClmantTxt() != null && castOther.getCntntnClmantTxt() != null
								&& this.getCntntnClmantTxt().equals(castOther.getCntntnClmantTxt())))
				&& ((this.getCntntnMedInd() == castOther.getCntntnMedInd())
						|| (this.getCntntnMedInd() != null && castOther.getCntntnMedInd() != null
								&& this.getCntntnMedInd().equals(castOther.getCntntnMedInd())))
				&& ((this.getCntntnWellGrndedAplcblInd() == castOther.getCntntnWellGrndedAplcblInd())
						|| (this.getCntntnWellGrndedAplcblInd() != null
								&& castOther.getCntntnWellGrndedAplcblInd() != null
								&& this.getCntntnWellGrndedAplcblInd()
										.equals(castOther.getCntntnWellGrndedAplcblInd())))
				&& ((this.getCntntnBeginDt() == castOther.getCntntnBeginDt())
						|| (this.getCntntnBeginDt() != null && castOther.getCntntnBeginDt() != null
								&& this.getCntntnBeginDt().equals(castOther.getCntntnBeginDt())))
				&& ((this.getCntntnSpeclIssueId() == castOther.getCntntnSpeclIssueId())
						|| (this.getCntntnSpeclIssueId() != null && castOther.getCntntnSpeclIssueId() != null
								&& this.getCntntnSpeclIssueId().equals(castOther.getCntntnSpeclIssueId())))
				&& ((this.getCntntnSpeclIssueTypeCd() == castOther.getCntntnSpeclIssueTypeCd())
						|| (this.getCntntnSpeclIssueTypeCd() != null && castOther.getCntntnSpeclIssueTypeCd() != null
								&& this.getCntntnSpeclIssueTypeCd().equals(castOther.getCntntnSpeclIssueTypeCd())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getPtcpntVetId();
		result = 37 * result + (getPrfilDt() == null ? 0 : this.getPrfilDt().hashCode());
		result = 37 * result + (int) this.getBnftClaimId();
		result = 37 * result + (getEndPrdctTypeCd() == null ? 0 : this.getEndPrdctTypeCd().hashCode());
		result = 37 * result + (getDateOfClaim() == null ? 0 : this.getDateOfClaim().hashCode());
		result = 37 * result + (getPayeeTypeCd() == null ? 0 : this.getPayeeTypeCd().hashCode());
		result = 37 * result + (getBnftClaimTypeCd() == null ? 0 : this.getBnftClaimTypeCd().hashCode());
		result = 37 * result + (getClaimLabel() == null ? 0 : this.getClaimLabel().hashCode());
		result = 37 * result + (getStatusTypeCd() == null ? 0 : this.getStatusTypeCd().hashCode());
		result = 37 * result + (int) this.getClaimRoNumber();
		result = 37 * result + (getClaimRoName() == null ? 0 : this.getClaimRoName().hashCode());
		result = 37 * result + (getCntntnId() == null ? 0 : this.getCntntnId().hashCode());
		result = 37 * result + (getCntntnClsfcnId() == null ? 0 : this.getCntntnClsfcnId().hashCode());
		result = 37 * result + (getCntntnTypeCd() == null ? 0 : this.getCntntnTypeCd().hashCode());
		result = 37 * result + (getCntntnClmantTxt() == null ? 0 : this.getCntntnClmantTxt().hashCode());
		result = 37 * result + (getCntntnMedInd() == null ? 0 : this.getCntntnMedInd().hashCode());
		result = 37 * result + (getCntntnWellGrndedAplcblInd() == null ? 0 : this.getCntntnWellGrndedAplcblInd().hashCode());
		result = 37 * result + (getCntntnBeginDt() == null ? 0 : this.getCntntnBeginDt().hashCode());
		result = 37 * result + (getCntntnSpeclIssueId() == null ? 0 : this.getCntntnSpeclIssueId().hashCode());
		result = 37 * result + (getCntntnSpeclIssueTypeCd() == null ? 0 : this.getCntntnSpeclIssueTypeCd().hashCode());
		return result;
	}

}
