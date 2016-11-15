package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema="BCDSS", name = "DDM_MODEL_PATTERN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DDMModelPattern implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long patternId;
	private String modelType;
	private Long claimantAge;
	private Long claimCount;
	private Long contentionCount;
	private Long priorCDD;
	private Long CDDAge;
	private DDMModelDiagnosis modelDiagnosis;
	private DDMModelContention modelContention;

	@Id
	@Column(name="PATTERN_ID")
	public Long getPatternId() {
		return patternId;
	}
	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}

	@Column(name="MODEL_TYPE")
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Column(name="CLAIMANT_AGE")
	public Long getClaimantAge() {
		return claimantAge;
	}
	public void setClaimantAge(Long claimantAge) {
		this.claimantAge = claimantAge;
	}

	@Column(name="CLAIM_COUNT")
	public Long getClaimCount() {
		return claimCount;
	}
	public void setClaimCount(Long claimCount) {
		this.claimCount = claimCount;
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
	public void setPriorCDD(Long priorCDD) {
		this.priorCDD = priorCDD;
	}

	@Column(name="CDD_AGE")
	public Long getCDDAge() {
		return CDDAge;
	}
	public void setCDDAge(Long CDDAge) {
		this.CDDAge = CDDAge;
	}

/*	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DDM_MODEL_DIAG")
	public DDMModelDiagnosis getModelDiagnosis() {
		return modelDiagnosis;
	}
	public void setModelDiagnosis(DDMModelDiagnosis modelDiagnosis) {
		this.modelDiagnosis = modelDiagnosis;
	}*/
}
