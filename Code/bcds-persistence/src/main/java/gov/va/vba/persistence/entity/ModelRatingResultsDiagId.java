package gov.va.vba.persistence.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ModelRatingResultsDiagId implements java.io.Serializable {

	private Long diagId;
	private Long processId;

	public ModelRatingResultsDiagId() {
	}

	public ModelRatingResultsDiagId(Long diagId, Long processId) {
		this.diagId = diagId;
		this.processId = processId;
	}

	@Column(name = "DIAG_CD")
	public Long getDiagId() {
		return this.diagId;
	}

	public void setDiagId(Long diagId) {
		this.diagId = diagId;
	}

	@Column(name = "PROCESS_ID")
	public Long getProcessId() {
		return this.processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ModelRatingResultsDiagId))
			return false;
		ModelRatingResultsDiagId castOther = (ModelRatingResultsDiagId) other;

		return ((this.getDiagId() == castOther.getDiagId()) || (this.getDiagId() != null
				&& castOther.getDiagId() != null && this.getDiagId().equals(castOther.getDiagId())))
				&& ((this.getProcessId() == castOther.getProcessId()) || (this.getProcessId() != null
						&& castOther.getProcessId() != null && this.getProcessId().equals(castOther.getProcessId())));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (getDiagId() == null ? 0 : this.getDiagId().hashCode());
		result = 37 * result + (getProcessId() == null ? 0 : this.getProcessId().hashCode());
		return result;
	}

}
