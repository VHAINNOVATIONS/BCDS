package gov.va.vba.web.rest;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import gov.va.vba.persistence.entity.DDMModelPattern;
import gov.va.vba.persistence.repository.DDMModelPatternRepository;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class ddmModelResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private DDMModelPatternRepository ddmModelPatternRepository;

    /**
     * GET  /users -> get all ddms.
     */
    @RequestMapping(value = "/ddms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DDMModelPattern> getAll() {
        LOGGER.debug("REST request to get all Users");
        return ddmModelPatternRepository.findAll();
    }

    /**
     * GET  /ddms/:patternId -> get the "patternId" ddm.
     */
/*    @RequestMapping(value = "/ddms/{patternId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    ResponseEntity<DDMModelPattern> getDDM(@PathVariable Long patternId) {
        LOGGER.debug("REST request to get DDM : {}", patternId);
        return ddmModelPatternRepository.findOneDDM(patternId)
                .map(ddmModelPattern -> new ResponseEntity<>(ddmModelPattern, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/
}
