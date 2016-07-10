package gov.va.vba.persistence.entity;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;

/**
 * Created by Pete Grazaitis on 2/9/2015.
 */
@Entity
@Table(name="REVISION", schema = "BCDSS_DEV")
@RevisionEntity
public class CustomEntityRevision {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    //@GeneratedValue(strategy= GenerationType.AUTO, generator="bcds_audit_sequence")
    //(name="bcds_audit_sequence", sequenceName="BCDS.AUDIT_SEQUENCE")
    @RevisionNumber
    private long id;

    @RevisionTimestamp
    private long timestamp;

}
