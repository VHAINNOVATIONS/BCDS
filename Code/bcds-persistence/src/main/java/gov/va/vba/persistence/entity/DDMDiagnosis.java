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
@Table(schema="BCDSS", name = "DDM_DIAG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DDMDiagnosis implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long diagnosisId;
	private Long categoryId;
	private Long diagnosisCode;
	private String diagnosisCodeDesc;
	private String createdBy;
	private Date createdDate;
	private String modelType;

	@Id
	@Column(name="DIAG_ID")
	public Long getDiagnosisId() {
		return diagnosisId;
	}
	public void setDiagnosisId(Long diagnosisId) {
		this.diagnosisId = diagnosisId;
	}

	@Column(name="CTLG_ID")
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name="DIAG_CD")
	public Long getDiagnosisCode() {
		return diagnosisCode;
	}
	public void setDiagnosisCode(Long diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	@Column(name="DIAG_CD_DESC")
	public String getDiagnosisCodeDesc() {
		return diagnosisCodeDesc;
	}
	public void setDiagnosisCodeDesc(String diagnosisCodeDesc) {
		this.diagnosisCodeDesc = diagnosisCodeDesc;
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

	@Column(name="MODEL_TYPE")
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
}
