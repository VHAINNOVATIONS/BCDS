package gov.va.vba.domain.util;

import java.io.Serializable;

public class ModelPatternIndex implements Serializable {
	private Long patternId;
	private Double accuracy;
	private Long CDD;
	private Long patternIndexNumber;
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
	public void setCDD(Long CDD) {
		this.CDD = CDD;
	}

	public Long getPatternIndexNumber() {
		return patternIndexNumber;
	}
	public void setPatternIndexNumber(Long patternIndexNumber) {
		this.patternIndexNumber = patternIndexNumber;
	}

	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
}
