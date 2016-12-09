package gov.va.vba.web.rest.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class ModelRatingResultsDTO {
	
	private Date fromDate;
    private Date toDate;
    private String modelType;
    private Long processId;
   

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

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }
}
