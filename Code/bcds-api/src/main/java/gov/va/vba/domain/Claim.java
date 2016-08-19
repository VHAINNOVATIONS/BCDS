package gov.va.vba.domain;

import java.io.Serializable;
import java.util.Date;


public class Claim implements Serializable{

	private Long veteranId;
	//private String veteranName;
	private String regionalOfficeOfClaim;
	private Long claimId;
	private Date claimDate;
	//private Date cestDate;
	private String contentionClaimTextKeyForModel;
	
	public Long getVeteranId() {
		return veteranId;
	}
	public void setVeteranId(Long veteranId) {
		this.veteranId = veteranId;
	}
	
	
	/*public String getVeteranName() {
		return "Veteran"+ getVeteranId().toString();
	}
	public void setVeteranName(String veteranName) {
		this.veteranName = veteranName;
	}*/
	
	public String getRegionalOfficeOfClaim() {
		return regionalOfficeOfClaim;
	}
	public void setRegionalOfficeOfClaim(String regionalOfficeOfClaim) {
		this.regionalOfficeOfClaim = regionalOfficeOfClaim;
	}
	
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}
	
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}
	
	
	/*public Date getCestDate() {
		return cestDate;
	}
	public void setCestDate(Date cestDate) {
		this.cestDate = cestDate;
	}*/
	
	public String getContentionClaimTextKeyForModel() {
		return contentionClaimTextKeyForModel;
	}
	public void setContentionClaimTextKeyForModel(String contentionClaimTextKeyForModel) {
		this.contentionClaimTextKeyForModel = contentionClaimTextKeyForModel;
	}
}
