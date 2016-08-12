package gov.va.vba.domain;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

public class Claim implements Serializable{

	private Long veteranId;
	//private String veteranName;
	private String regionalOfficeOfClaim;
	private Long claimId;
	private LocalDateTime claimDate;
	//private LocalDateTime cestDate;
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
	
	public LocalDateTime getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(LocalDateTime claimDate) {
		this.claimDate = claimDate;
	}
	
	
	/*public LocalDateTime getCestDate() {
		return cestDate;
	}
	public void setCestDate(LocalDateTime cestDate) {
		this.cestDate = cestDate;
	}*/
	
	public String getContentionClaimTextKeyForModel() {
		return contentionClaimTextKeyForModel;
	}
	public void setContentionClaimTextKeyForModel(String contentionClaimTextKeyForModel) {
		this.contentionClaimTextKeyForModel = contentionClaimTextKeyForModel;
	}
	
	
	
	
}
