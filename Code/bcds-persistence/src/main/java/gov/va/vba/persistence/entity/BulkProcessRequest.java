package gov.va.vba.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema = "BCDSS", name = "BULK_PROCESS_REQUEST")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BulkProcessRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long requestId;
	private Date requestDate;
	private Date fromDate;
	private Date toDate;
	private Long regionalOfficeOfClaim;
	private String modelType;
	private String crtdBy;
	private String requestStatus;
	private Date completedDate;
	private Long recordCount;
	private String notes;
	
	@Id
	@Column(name = "REQUEST_ID")
	public Long getRequestId() {
		return this.requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "REQUEST_DATE")
	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FROM_DATE")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TO_DATE")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	@Column(name = "RO_NAME")
	public Long getRegionalOfficeOfClaim() {
		return regionalOfficeOfClaim;
	}

	public void setRegionalOfficeOfClaim(Long regionalOfficeOfClaim) {
		this.regionalOfficeOfClaim = regionalOfficeOfClaim;
	}
	
	@Column(name="MODEL_TYPE")
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	
	@Column(name = "REQUEST_STATUS", length = 50)
	public String getRequestStatus() {
		return this.requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "COMPLETED_DATE")
	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	
	@Column(name = "CRTD_BY", length = 50)
    public String getCrtdBy() {
        return this.crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }
    
	@Column(name = "RECORD_COUNT")
	public Long getRecordCount() {
		return this.recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	
	@Column(name = "NOTES", length = 100)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
