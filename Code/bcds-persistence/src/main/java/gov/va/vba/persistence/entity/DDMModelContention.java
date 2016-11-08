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
@Table(schema="BCDSS", name = "DDM_MODEL_CNTNT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DDMModelContention implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long patternId;
	private Long contentionId;
	private Long count;
	private String modelType;

	@Id
	@Column(name="PATTERN_ID")
	public Long getPatternId() {
		return patternId;
	}
	public void setPatternId(Long patternId) {
		this.patternId = patternId;
	}

	@Column(name="CNTNT_ID")
	public Long getContentionId() {
		return contentionId;
	}
	public void setContentionId(Long contentionId) {
		this.contentionId = contentionId;
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
}
