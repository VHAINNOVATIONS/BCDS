package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class EditModelPatternResults implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long patternId;
	private Double accuracy;
	private Long CDD;
	private Long patternIndexNumber;
	private String createdBy;
	private Date createdDate;
	private int categoryId;
	private String modelType;
	public Long getPatternId() {
		return patternId;
	}
	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
	public Long getCDD() {
		return CDD;
	}
	public void setCDD(Long cDD) {
		CDD = cDD;
	}
	public Long getPatternIndexNumber() {
		return patternIndexNumber;
	}
	public void setPatternIndexNumber(Long patternIndexNumber) {
		this.patternIndexNumber = patternIndexNumber;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
}
