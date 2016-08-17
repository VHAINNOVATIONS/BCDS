package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema="BCDSS", name = "AH4929_RATING_CORP_CLAIM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Claim implements Serializable {

	//private static final long serialVersionUID = 1L;
	private Long veteranId;
	private String veteranName;
	private String regionalOfficeOfClaim;
	private Long claimId;
	private Date claimDate;
	//private LocalDateTime cestDate;
	private String contentionClaimTextKeyForModel;

	@Id
	@Column(name="PTCPNT_VET_ID")
	public Long getVeteranId() {
		return veteranId;
	}
	public void setVeteranId(Long veteranId) {
		this.veteranId = veteranId;
	}
	
	public String getVeteranName() {
		return "Veteran"+ getVeteranId().toString();
	}
	public void setVeteranName(String veteranName) {
		this.veteranName = veteranName;
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

	/*public LocalDateTime getCestDate() {
		return cestDate;
	}
	public void setCestDate(LocalDateTime cestDate) {
		this.cestDate = cestDate;
	}*/

	@Column(name="CNTNTN_CLMANT_TXT")
	public String getContentionClaimTextKeyForModel() {
		return contentionClaimTextKeyForModel;
	}
	public void setContentionClaimTextKeyForModel(String contentionClaimTextKeyForModel) {
		this.contentionClaimTextKeyForModel = contentionClaimTextKeyForModel;
	}
}
