package gov.va.vba.web.rest.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class BulkProcessClaimDTO implements Serializable {

	private Date fromDate;
    private Date toDate;
    private String modelType;
    private Long regionalOfficeNumber;
    private boolean saveParams;
    private String userId;
    private Long recordCount;
    
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

    public void setContentionType(String modelType) {
        this.modelType = modelType;
    }

    public Long getRegionalOfficeNumber() {
        return regionalOfficeNumber;
    }

    public void setRegionalOfficeNumber(Long regionalOfficeNumber) {
        this.regionalOfficeNumber = regionalOfficeNumber;
    }
    
    public Boolean getSaveParams() {
        return saveParams;
    }

    public void setSaveParams(Boolean saveParams) {
        this.saveParams = saveParams;
    }
    
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Long getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }
}
