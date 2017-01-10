package gov.va.vba.web.rest.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Component
public class ModelRatingResultsDTO {
	
	private Date fromDate;
    private Date toDate;
    private String modelType;
    private List<Long> processIds;
    private List<String> resultsStatus;
	private String crtdBy;
	private String reportType;
	private Long patternId;
	private Long cdd;
	private Long categoryId;
	private Double accuracy;
	private Long patternIndexNumber;
		
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public List<Long> getProcessIds() {
        return processIds;
    }

    public void setProcessIds(List<Long> processIds) {
        this.processIds = processIds;
    }
    
    public List<String> getResultsStatus() {
        return resultsStatus;
    }

    public void setResultsStatus(List<String> resultsStatus) {
        this.resultsStatus = resultsStatus;
    }
    
    public String getCrtdBy() {
        return this.crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }
    
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
	
	public Long getPatternIndexNumber() {
		return patternIndexNumber;
	}
	public void setPatternIndexNumber(Long patternIndexNumber) {
		this.patternIndexNumber = patternIndexNumber;
	}
	
    public Long getCDD() {
        return cdd;
    }

    public void setCDD(Long cdd) {
        this.cdd = cdd;
    }
    
    public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
    public String getReportType() {
        return this.reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
