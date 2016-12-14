package gov.va.vba.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ModelRatingResultsStatusId implements java.io.Serializable {

	private Long processId;
	private String processStatus;

	public ModelRatingResultsStatusId() {
	}

	public ModelRatingResultsStatusId(Long processId, String processStatus) {
		this.processId = processId;
		this.processStatus = processStatus;
	}

	@Column(name = "PROCESS_ID")
	public Long getProcessId() {
		return this.processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@Column(name = "PROCESS_STATUS", length = 50)
	public String getProcessStatus() {
		return this.processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ModelRatingResultsStatusId))
			return false;
		ModelRatingResultsStatusId castOther = (ModelRatingResultsStatusId) other;

		return ((this.getProcessId() == castOther.getProcessId()) || (this.getProcessId() != null
				&& castOther.getProcessId() != null && this.getProcessId().equals(castOther.getProcessId())))
				&& ((this.getProcessStatus() == castOther.getProcessStatus())
						|| (this.getProcessStatus() != null && castOther.getProcessStatus() != null
								&& this.getProcessStatus().equals(castOther.getProcessStatus())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getProcessId() == null ? 0 : this.getProcessId().hashCode());
		result = 37 * result + (getProcessStatus() == null ? 0 : this.getProcessStatus().hashCode());
		return result;
	}

}
