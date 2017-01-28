package gov.va.vba.domain;

import java.io.Serializable;

import gov.va.vba.domain.util.Veteran;

public class ServerRequestStatus implements Serializable {
	private String status;
	private String reason;
	private String error;
	private Long recordCount;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
}
