package gov.va.vba.persistence.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.*;
import java.util.UUID;

/**
 * Created by Pete Grazaitis on 4/2/2015.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private Long id;

    private Integer version;

    private UUID objectId = UUID.randomUUID();

    /**
     * get the ID for the master record
     * @return master record ID
     */
    /**record ID. */
    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    @SequenceGenerator(name = "SEQUENCE_GEN", sequenceName = "BCDS.BCDS_HIBERNATE_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_GEN")
    public Long getId(){
        return id;
    }

    /**
     * set the ID for the master record
     * @param id ID for the master record
     */
    public void setId(Long id){
        this.id = id;
    }


    /**
     * @return the version
     */
    @Version
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name="OBJECT_ID")
    @NotAudited
    public UUID getObjectId() {
        if(objectId == null) {
            objectId = UUID.randomUUID();
        }

        return  objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    protected void clearIdentity() {
        setObjectId(UUID.randomUUID()); //Grab unique identifer to leverage. Business logic assumes this will never be null.
        setId(null); //Persistence id is generated upon flush, clearing will allow generation mechanism to create a new one.
        //setLogicalId(null); //TODO: Leverage a generation technique similar to current model of -1,-2, etc for BGS
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || !(o instanceof AbstractEntity)) return false;

        AbstractEntity other = (AbstractEntity)o;
        if (this.getObjectId() == null) return false;
        else if (this.getObjectId() == other.getObjectId()) return true;

        return this.getObjectId().equals(other.getObjectId());
    }

    @Override
    public int hashCode() {
        if (this.getObjectId() != null) {
            return this.getObjectId().hashCode();
        } else {
            return super.hashCode(); //This should never fire.
        }
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}
