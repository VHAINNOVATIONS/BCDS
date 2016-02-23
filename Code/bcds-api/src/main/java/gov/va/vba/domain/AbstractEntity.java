package gov.va.vba.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.*;
import java.util.UUID;

/**
 * Created by Pete Grazaitis on 4/2/2015.
 */
public abstract class AbstractEntity implements Serializable {

    private Long id;

    private Integer version;

    private UUID objectId = UUID.randomUUID();

    /**
     * get the ID for the master record
     * @return master record ID
     */
    /**record ID. */
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
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

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
    /***
     * This will perform a deep clone of the object assuming it is an AbstractEntity
     * @param origObject
     * @param isIdentical True will create a fully identical object, including from an equals standpoint and persistence standpoint.
     * @param <T> Subtype of an Abstract Entity, which is the base persistence class.
     * @return
     */
    public static <T extends AbstractEntity> T performClone(T origObject,boolean isIdentical){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(origObject);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            //OK to not check instance of here, since both input and output streams use object of type T, they will always be the same.
            T clonedObj = (T)ois.readObject();
            ois.close();

            if(!isIdentical) {
                clonedObj.clearIdentity();
            }
            return clonedObj;
        } catch (IOException|ClassNotFoundException e) {
            return null;
        }
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
