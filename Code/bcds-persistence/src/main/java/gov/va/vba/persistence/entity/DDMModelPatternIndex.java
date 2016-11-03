package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema="BCDSS", name = "DDM_MODEL_PATTERN_INDX")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DDMModelPatternIndex implements Serializable {

	private Long patternId;
	private Long accuracy;
	private Long CDD;
	private Long patternIndexNumber;
	private String createdBy;
	private Date createdDate;
	private Long ctlgId;
	private String modelType;

	@Id
	@Column(name="PATTERN_ID")
	public Long getPatternId() {
		return patternId;
	}
	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}

	@Column(name="ACCURACY")
	public Long getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Long accuracy) {
		this.accuracy = accuracy;
	}

	@Column(name="CDD")
	public Long getCDD() {
		return CDD;
	}
	public void setCDD(Long CDD) {
		this.CDD = CDD;
	}

	@Column(name="PATTERN_INDEX_NUMBER")
	public Long getPatternIndexNumber() {
		return patternIndexNumber;
	}
	public void setPatternIndexNumber(Long patternIndexNumber) {
		this.patternIndexNumber = patternIndexNumber;
	}

	@Column(name="CRTD_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="CRTD_DTM")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="CTLG_ID")
	public Long getCtlgId() {
		return ctlgId;
	}
	public void setCtlgId(Long ctlgId) {
		this.ctlgId = ctlgId;
	}

	@Column(name="MODEL_TYPE")
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

}