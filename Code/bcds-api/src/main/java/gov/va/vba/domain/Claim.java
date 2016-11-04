package gov.va.vba.domain;

import gov.va.vba.domain.util.Veteran;

import java.io.Serializable;
import java.util.Date;


public class Claim implements Serializable{

	private Veteran veteran;
	private String regionalOfficeOfClaim;
	private Long claimId;
	private Date claimDate;
	private String contentionClaimTextKeyForModel;

	public Veteran getVeteran() {
		return veteran;
	}


	public void setVeteran(Veteran veteran) {
		this.veteran = veteran;
	}

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
	
	public String getContentionClaimTextKeyForModel() {
		return contentionClaimTextKeyForModel;
	}
	public void setContentionClaimTextKeyForModel(String contentionClaimTextKeyForModel) {
		this.contentionClaimTextKeyForModel = contentionClaimTextKeyForModel;
	}
}
