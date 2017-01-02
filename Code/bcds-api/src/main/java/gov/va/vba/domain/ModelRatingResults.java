package gov.va.vba.domain;

import gov.va.vba.domain.util.Veteran;
import gov.va.vba.domain.util.ModelPatternIndex;

import java.io.Serializable;
import java.util.Date;


public class ModelRatingResults implements Serializable{

	private Long processId;
	private Veteran veteran;
	private ModelPatternIndex patternIndex;
	private Date processDate;
	private Long claimId;
	private Date claimDate;
	private Long claimAge;
	private String modelType;
	private Long priorCDD;
	private Long quantPriorCDD;
	private Long currentCDD;
	private Long quantCDD;
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

	public ModelPatternIndex getPatternIndex() {
		return patternIndex;
	}

	public void setPatternIndex(ModelPatternIndex patternIndex) {
		this.patternIndex = patternIndex;
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
	
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}
	
	public Long getClaimAge() {
		return claimAge;
	}
	public void setClaimAge(Long claimAge) {
		this.claimAge = claimAge;
	}

	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
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