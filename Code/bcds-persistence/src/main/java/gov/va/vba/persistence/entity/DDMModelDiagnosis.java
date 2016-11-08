package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema="BCDSS", name = "DDM_MODEL_DIAG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DDMModelDiagnosis implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long patternId;
	private Long diagnosisId;
	private Long count;
	private String modelType;
//	private Set<DDMModelPattern> modelPattern = new HashSet<>();

	@Id
	@Column(name="PATTERN_ID")
	public Long getPatternId() {
		return patternId;
	}
	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}

	@Column(name="DIAG_ID")
	public Long getDiagnosisId() {
		return diagnosisId;
	}
	public void setDiagnosisId(Long diagnosisId) {
		this.diagnosisId = diagnosisId;
	}

	@Column(name="COUNT")
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	@Column(name="MODEL_TYPE")
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modelPattern")
	public Set<DDMModelPattern> getModelPattern() {
		return modelPattern;
	}
	public void setModelPattern(Set<DDMModelPattern> modelPattern) {
		this.modelPattern = modelPattern;
	}*/
}
