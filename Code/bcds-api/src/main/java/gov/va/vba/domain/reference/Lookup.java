package gov.va.vba.domain.reference;

import gov.va.vba.domain.AbstractEntity;

import javax.persistence.*;


/**
 * A Lookup.
 */
public class Lookup extends AbstractEntity {

    protected String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
