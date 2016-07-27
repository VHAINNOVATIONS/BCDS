package gov.va.vba.persistence.entity.reference;

import gov.va.vba.persistence.entity.AbstractEntity;

import javax.persistence.*;


/**
 * A Lookup.
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "discriminator")
@Table(schema="BCDSS", name="LOOKUP")
public class Lookup extends AbstractEntity {

    protected String name;

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
