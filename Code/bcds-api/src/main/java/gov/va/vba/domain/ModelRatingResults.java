package gov.va.vba.domain;

import gov.va.vba.domain.util.Veteran;

import java.io.Serializable;
import java.util.Date;


public class ModelRatingResults implements Serializable{

	private Long processId;
	private Veteran veteran;
	private Long patternId;
	private Date processDate;
	private Long claimId;
	private Long claimantAge;
	private Date dateOfBirth;
	private String endProductCode;
	private Long regionalOfficeNumber;
	private Date claimDate;
	private Date profileDate;
	private Date promulgationDate;
	private Date recentDate;
	private String modelType;
	private Long modelContentionCount;
	private Long contentionCount;
	private Long priorCDD;
	private Long quantPriorCDD;
	private Long currentCDD;
	private Long quantCDD;
	private Long claimAge;
	private Long CDDAge;
	private Long claimCount;

	
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Veteran getVeteran() {
		return veteran;
	}

	public void setVeteran(Veteran veteran) {
		this.veteran = veteran;
	}

	public Long getPatternId() {
		return patternId;
	}
	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}

	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public Long getClaimantAge() {
		return claimantAge;
	}
	
	public void setClaimantAge(Long claimantAge) {
		this.claimantAge = claimantAge;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEndProductCode() {
		return endProductCode;
	}
	public void setEndProductCode(String endProductCode) {
		this.endProductCode = endProductCode;
	}

	public Long getRegionalOfficeNumber() {
		return regionalOfficeNumber;
	}
	public void setRegionalOfficeNumber(Long regionalOfficeNumber) {
		this.regionalOfficeNumber = regionalOfficeNumber;
	}

	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public Date getProfileDate() {
		return profileDate;
	}
	public void setProfileDate(Date profileDate) {
		this.profileDate = profileDate;
	}

	public Date getPromulgationDate() {
		return promulgationDate;
	}
	public void setPromulgationDate(Date promulgationDate) {
		this.promulgationDate = promulgationDate;
	}

	public Date getRecentDate() {
		return recentDate;
	}
	public void setRecentDate(Date recentDate) {
		this.recentDate = recentDate;
	}

	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public Long getModelContentionCount() {
		return modelContentionCount;
	}
	public void setModelContentionCount(Long modelContentionCount) {
		this.modelContentionCount = modelContentionCount;
	}

	public Long getContentionCount() {
		return contentionCount;
	}
	public void setContentionCount(Long contentionCount) {
		this.contentionCount = contentionCount;
	}

	public Long getPriorCDD() {
		return priorCDD;
	}
	public void setPriorCDD(Long priorCdd) {
		this.priorCDD = priorCdd;
	}

	public Long getQuantPriorCDD() {
		return quantPriorCDD;
	}
	public void setQuantPriorCDD(Long quantPriorCdd) {
		this.quantPriorCDD = quantPriorCdd;
	}

	public Long getCurrentCDD() {
		return currentCDD;
	}
	public void setCurrentCDD(Long currentCdd) {
		this.currentCDD = currentCdd;
	}

	public Long getQuantCDD() {
		return quantCDD;
	}
	public void setQuantCDD(Long quantCdd) {
		this.quantCDD = quantCdd;
	}

	public Long getClaimAge() {
		return claimAge;
	}
	public void setClaimAge(Long claimAge) {
		this.claimAge = claimAge;
	}

	public Long getCDDAge() {
		return CDDAge;
	}
	public void setCDDAge(Long CDDAge) {
		this.CDDAge = CDDAge;
	}

	public Long getClaimCount() {
		return claimCount;
	}
	public void setClaimCount(Long claimCount) {
		this.claimCount = claimCount;
	}
}