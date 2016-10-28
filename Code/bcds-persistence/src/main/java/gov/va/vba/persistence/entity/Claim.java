package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema="BCDSS", name = "AH4929_RATING_CORP_CLAIM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Claim implements Serializable {

	private Long veteranId;
	private Date prfilDate;
	private long claimId;
	private String endPrdctTypeCode;
	private Date claimDate;
	private String payeeTypeCode;
	private String bnftClaimTypeCode;
	private String claimLabel;
	private String statusTypeCode;
	private long regionalOfficeNumberOfClaim;
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
	
	private Set<Veteran> veteran;

	@Id
	@Column(name="PTCPNT_VET_ID")
	public Long getVeteranId() {
		return veteranId;
	}
	public void setVeteranId(Long veteranId) {
		this.veteranId = veteranId;
	}
	
	@Column(name="CLAIM_RO_NAME")
	public String getRegionalOfficeOfClaim() {
		return regionalOfficeOfClaim;
	}
	public void setRegionalOfficeOfClaim(String regionalOfficeOfClaim) {
		this.regionalOfficeOfClaim = regionalOfficeOfClaim;
	}

	@Column(name="BNFT_CLAIM_ID")
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	@Column(name="DATE_OF_CLAIM")
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	@Column(name="CNTNTN_CLMANT_TXT")
	public String getContentionClaimTextKeyForModel() {
		return contentionClaimTextKeyForModel;
	}
	public void setContentionClaimTextKeyForModel(String contentionClaimTextKeyForModel) {
		this.contentionClaimTextKeyForModel = contentionClaimTextKeyForModel;
	}
}
