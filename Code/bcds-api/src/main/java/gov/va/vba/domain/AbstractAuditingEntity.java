package gov.va.vba.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;


/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
public abstract class AbstractAuditingEntity {

    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private DateTime createdDate = DateTime.now();

    @JsonIgnore
    private String lastModifiedBy;

    @JsonIgnore
    private DateTime lastModifiedDate = DateTime.now();

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public DateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(DateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
