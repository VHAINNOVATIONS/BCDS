package gov.va.vba.domain;

import gov.va.vba.domain.util.ModelPatternIndex;

import java.io.Serializable;
import java.util.Date;

public class ModelRatingPattern implements Serializable {
	
	private ModelPatternIndex patternIndex;
	private String createdBy;
	private Date createdDate;
	private Long categoryId;
	
	public ModelPatternIndex getPatternIndex() {
		return patternIndex;
	}

	public void setPatternIndex(ModelPatternIndex patternIndex) {
		this.patternIndex = patternIndex;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
}
