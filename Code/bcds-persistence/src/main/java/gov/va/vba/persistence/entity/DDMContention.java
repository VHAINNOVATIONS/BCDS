package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema="BCDSS", name = "DDM_CNTNT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DDMContention implements Serializable {

	private Long contentionId;
	private Long categoryId;
	private Long contentionCode;
	private String modelType;
	private String contentionCodeDesc;
	private String createdBy;
	private Date createdDate;

	@Column(name="CNTNT_ID")
	public Long getContentionId() {
		return contentionId;
	}
	public void setContentionId(Long contentionId) {
		this.contentionId = contentionId;
	}

	@Column(name="CTLG_ID")
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name="CNTNT_CD")
	public Long getContentionCode() {
		return contentionCode;
	}
	public void setContentionCode(Long contentionCode) {
		this.contentionCode = contentionCode;
	}

	@Column(name="MODEL_TYPE")
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Column(name="CNTNT_CD_DESC")
	public String getContentionCodeDesc() {
		return contentionCodeDesc;
	}
	public void setContentionCodeDesc(String contentionCodeDesc) {
		this.contentionCodeDesc = contentionCodeDesc;
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
}
