package gov.va.vba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class ModelRatingResultsDiag implements Serializable {
	private BigDecimal diagId;
	private Long processId;
	private BigDecimal count;

	public BigDecimal getDiagId() {
		return diagId;
	}
	public void setDiagId(BigDecimal diagId) {
		this.diagId = diagId;
	}

	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	public BigDecimal getCount() {
		return count;
	}
	public void setCount(BigDecimal count) {
		this.count = count;
	}
}
