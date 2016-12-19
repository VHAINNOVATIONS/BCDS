package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(schema="BCDSS", name = "MODEL_RATING_RESULTS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModelRatingResults implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long processId;
	private Long veteranId;
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
	private Veteran veteran;
	private DDMModelPatternIndex patternIndex;
	
	@Id
	@Column(name="PROCESS_ID")
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@Column(name="PTCPNT_VET_ID")
	public Long getVeteranId() {
		return veteranId;
	}
	public void setVeteranId(Long veteranId) {
		this.veteranId = veteranId;
	}
	
	@MapsId("veteranId")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PTCPNT_VET_ID", referencedColumnName="PTCPNT_VET_ID", insertable = false, updatable = false)
	public Veteran getVeteran() {
		return veteran;
	}

	public void setVeteran(Veteran veteran) {
		this.veteran = veteran;
	}
	
	@Column(name="PATTERN_ID")
	public Long getPatternId() {
		return patternId;
	}
	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}
	
	@MapsId("patternId")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PATTERN_ID", referencedColumnName="PATTERN_ID", insertable = false, updatable = false)
	public DDMModelPatternIndex getPatternIndex() {
		return patternIndex;
	}

	public void setPatternIndex(DDMModelPatternIndex patternIndex) {
		this.patternIndex = patternIndex;
	}

	@Column(name="PROCESS_DATE")
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	@Column(name="CLAIM_ID")
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	@Column(name="CLAIMANT_AGE")
	public Long getClaimantAge() {
		return claimantAge;
	}
	public void setClaimantAge(Long claimantAge) {
		this.claimantAge = claimantAge;
	}

	@Column(name="DOB")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name="END_PRODUCT_CODE")
	public String getEndProductCode() {
		return endProductCode;
	}
	public void setEndProductCode(String endProductCode) {
		this.endProductCode = endProductCode;
	}

	@Column(name="RO_NUMBER")
	public Long getRegionalOfficeNumber() {
		return regionalOfficeNumber;
	}
	public void setRegionalOfficeNumber(Long regionalOfficeNumber) {
		this.regionalOfficeNumber = regionalOfficeNumber;
	}

	@Column(name="CLAIM_DATE")
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	@Column(name="PROFILE_DATE")
	public Date getProfileDate() {
		return profileDate;
	}
	public void setProfileDate(Date profileDate) {
		this.profileDate = profileDate;
	}

	@Column(name="PROMULGATION_DATE")
	public Date getPromulgationDate() {
		return promulgationDate;
	}
	public void setPromulgationDate(Date promulgationDate) {
		this.promulgationDate = promulgationDate;
	}

	@Column(name="RECENT_DATE")
	public Date getRecentDate() {
		return recentDate;
	}
	public void setRecentDate(Date recentDate) {
		this.recentDate = recentDate;
	}

	@Column(name="MODEL_TYPE")
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Column(name="MODEL_CONTENTION_COUNT")
	public Long getModelContentionCount() {
		return modelContentionCount;
	}
	public void setModelContentionCount(Long modelContentionCount) {
		this.modelContentionCount = modelContentionCount;
	}

	@Column(name="CONTENTION_COUNT")
	public Long getContentionCount() {
		return contentionCount;
	}
	public void setContentionCount(Long contentionCount) {
		this.contentionCount = contentionCount;
	}

	@Column(name="PRIOR_CDD")
	public Long getPriorCDD() {
		return priorCDD;
	}
	public void setPriorCDD(Long priorCdd) {
		this.priorCDD = priorCdd;
	}

	@Column(name="QUANT_PRIOR_CDD")
	public Long getQuantPriorCDD() {
		return quantPriorCDD;
	}
	public void setQuantPriorCDD(Long quantPriorCdd) {
		this.quantPriorCDD = quantPriorCdd;
	}

	@Column(name="CURR_CDD")
	public Long getCurrentCDD() {
		return currentCDD;
	}
	public void setCurrentCDD(Long currentCdd) {
		this.currentCDD = currentCdd;
	}

	@Column(name="QUANT_CDD")
	public Long getQuantCDD() {
		return quantCDD;
	}
	public void setQuantCDD(Long quantCdd) {
		this.quantCDD = quantCdd;
	}

	@Column(name="CLAIM_AGE")
	public Long getClaimAge() {
		return claimAge;
	}
	public void setClaimAge(Long claimAge) {
		this.claimAge = claimAge;
	}

	@Column(name="CDD_AGE")
	public Long getCDDAge() {
		return CDDAge;
	}
	public void setCDDAge(Long CDDAge) {
		this.CDDAge = CDDAge;
	}

	@Column(name="CLAIM_COUNT")
	public Long getClaimCount() {
		return claimCount;
	}
	public void setClaimCount(Long claimCount) {
		this.claimCount = claimCount;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
