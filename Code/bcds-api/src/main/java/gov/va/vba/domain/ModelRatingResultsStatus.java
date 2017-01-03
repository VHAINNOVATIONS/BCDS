package gov.va.vba.domain;

import java.io.Serializable;

public class ModelRatingResultsStatus implements Serializable{
	
	private Long processId;
	private String processStatus;
	
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	public String getProcessStatus() {
		return this.processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
}
