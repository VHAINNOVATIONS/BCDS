package gov.va.vba.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.va.vba.domain.reference.Lookup;
import gov.va.vba.persistence.repository.LookupRepository;
import gov.va.vba.service.data.LookupDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Lookup.
 */
@RestController
@RequestMapping("/api")
public class LookupResource {

    @Inject
    private LookupDataService lookupDataService;


    /**
     * GET  /criteria -> get all the criteria.
     */
    @RequestMapping(value = "/lookup",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Lookup>> getAll(@RequestParam(value = "type" , required = false) String type,
                                               @RequestParam(value = "referenceId" , required = false) Long referenceId)
        throws URISyntaxException {
        if (type != null && referenceId != null){
        	return new ResponseEntity<>(lookupDataService.findByTypeAndReferenceId(type,referenceId), HttpStatus.OK);
        } else {
        	return new ResponseEntity<>(lookupDataService.findAll(), HttpStatus.OK);
        }
        
    }
}
