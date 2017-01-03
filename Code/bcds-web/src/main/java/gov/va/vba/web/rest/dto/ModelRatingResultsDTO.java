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
}
