package gov.va.vba.service.data;

import gov.va.vba.domain.reference.Lookup;
import gov.va.vba.persistence.repository.LookupRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pete Grazaitis on 10/7/2015.
 */
@Service
public class LookupDataService extends AbsDataService<gov.va.vba.persistence.entity.reference.Lookup, Lookup> {

    public LookupDataService() {
        this.setClasses(gov.va.vba.persistence.entity.reference.Lookup.class, Lookup.class);
    }

    public List<Lookup> findByTypeAndReferenceId(String type, Long referenceId) {
        List<Lookup> output = new ArrayList<>();
        List<gov.va.vba.persistence.entity.reference.Lookup> input = ((LookupRepository)repository).findByTypeAndReferenceId(type, referenceId);
        mapper.mapAsCollection(input,output,outputClass);

        return output;
    }
}
