package gov.va.vba.service.impl;

import gov.va.vba.persistence.entity.Veteran;
import gov.va.vba.persistence.repository.VeteranRepository;
import gov.va.vba.service.VeteranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ProSphere User on 11/2/2016.
 */
@Service
public class VeteranServiceImpl implements VeteranService {

    @Autowired
    private VeteranRepository veteranRepository;

    @Override
    public Veteran getVeteranById(Long veteranId) {
        return veteranRepository.findOneByVeteranId(veteranId);
    }
}
