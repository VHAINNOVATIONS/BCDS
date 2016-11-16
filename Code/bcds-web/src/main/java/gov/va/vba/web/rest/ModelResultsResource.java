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

import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.repository.ModelRatingResultsRepository;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class ModelResultsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelResultsResource.class);

    @Inject
    private ModelRatingResultsRepository modelRatingResultsRepository;

    /**
     * GET  /results -> get all results.
     */
    @RequestMapping(value = "/results",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ModelRatingResults> getAll() {
        LOGGER.debug("REST request to get all results");
        return modelRatingResultsRepository.findTop50();
    }

    /**
     * GET  /ddms/:processId -> get the "processId" results.
     */
/*    @RequestMapping(value = "/results/{processId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    ResponseEntity<ModelRatingResults> getDDM(@PathVariable Long patternId) {
        LOGGER.debug("REST request to get result : {}", processId);
        return modelRatingResultsRepository.findOneResult(processId)
                .map(modelRatingResults -> new ResponseEntity<>(modelRatingResults, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/
}
