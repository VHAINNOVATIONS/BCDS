package gov.va.vba.persistence.models.data;

import java.util.Date;

/**
 * Created by ProSphere User on 1/11/2017.
 */
public class ContentionDetails {

    private long contentionId;
    private long catalogId;
    private long contentionCode;
    private String modelType;
    private String contentionText;
    private String createdBy;
    private Date createdDate;

    public long getContentionId() {
        return contentionId;
    }

    public void setContentionId(long contentionId) {
        this.contentionId = contentionId;
    }

    public long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

    public long getContentionCode() {
        return contentionCode;
    }

    public void setContentionCode(long contentionCode) {
        this.contentionCode = contentionCode;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getContentionText() {
        return contentionText;
    }

    public void setContentionText(String contentionText) {
        this.contentionText = contentionText;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ContentionDetails{" +
                "contentionId=" + contentionId +
                ", catalogId=" + catalogId +
                ", contentionCode=" + contentionCode +
                ", modelType='" + modelType + '\'' +
                ", contentionText='" + contentionText + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

}
