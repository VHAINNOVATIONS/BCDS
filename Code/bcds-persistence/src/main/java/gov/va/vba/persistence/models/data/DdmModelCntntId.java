package main.java.gov.va.vba.persistence.models.data;
// Generated Oct 21, 2016 1:08:50 PM by Hibernate Tools 5.2.0.Beta1

import java.math.BigDecimal;

/**
 * DdmModelCntntId generated by hbm2java
 */
public class DdmModelCntntId implements java.io.Serializable {

	private BigDecimal patternId;
	private BigDecimal cntntId;
	private BigDecimal count;
	private String modelType;

	public DdmModelCntntId() {
	}

	public DdmModelCntntId(BigDecimal patternId, BigDecimal cntntId, BigDecimal count, String modelType) {
		this.patternId = patternId;
		this.cntntId = cntntId;
		this.count = count;
		this.modelType = modelType;
	}

	public BigDecimal getPatternId() {
		return this.patternId;
	}

	public void setPatternId(BigDecimal patternId) {
		this.patternId = patternId;
	}

	public BigDecimal getCntntId() {
		return this.cntntId;
	}

	public void setCntntId(BigDecimal cntntId) {
		this.cntntId = cntntId;
	}

	public BigDecimal getCount() {
		return this.count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DdmModelCntntId))
			return false;
		DdmModelCntntId castOther = (DdmModelCntntId) other;

		return ((this.getPatternId() == castOther.getPatternId()) || (this.getPatternId() != null
				&& castOther.getPatternId() != null && this.getPatternId().equals(castOther.getPatternId())))
				&& ((this.getCntntId() == castOther.getCntntId()) || (this.getCntntId() != null
						&& castOther.getCntntId() != null && this.getCntntId().equals(castOther.getCntntId())))
				&& ((this.getCount() == castOther.getCount()) || (this.getCount() != null
						&& castOther.getCount() != null && this.getCount().equals(castOther.getCount())))
				&& ((this.getModelType() == castOther.getModelType()) || (this.getModelType() != null
						&& castOther.getModelType() != null && this.getModelType().equals(castOther.getModelType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPatternId() == null ? 0 : this.getPatternId().hashCode());
		result = 37 * result + (getCntntId() == null ? 0 : this.getCntntId().hashCode());
		result = 37 * result + (getCount() == null ? 0 : this.getCount().hashCode());
		result = 37 * result + (getModelType() == null ? 0 : this.getModelType().hashCode());
		return result;
	}

}
