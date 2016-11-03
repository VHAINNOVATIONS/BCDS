package gov.va.vba.service;

import gov.va.vba.persistence.entity.Veteran;

/**
 * Created by ProSphere User on 11/2/2016.
 */
public interface VeteranService {

    Veteran getVeteranById(Long veteranId);
}
