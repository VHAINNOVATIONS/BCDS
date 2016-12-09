package gov.va.vba.web.rest;


import java.util.List;

import javax.inject.Inject;

import gov.va.vba.bcdss.models.BcdsModelingPort;
import gov.va.vba.bcdss.models.BcdsModelingPortService;

import gov.va.vba.web.rest.dto.ModelRatingResultsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;

import gov.va.vba.domain.ModelRatingResults;
import gov.va.vba.service.data.ModelRatingResultsDataService;
/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class ModelResultsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelResultsResource.class);

    @Inject
	private ModelRatingResultsDataService modelRatingResultsDataService;
    
    /**
     * GET  /results -> get all results.
     */
    @RequestMapping(value = "/results",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ModelRatingResults> getAll() {
        LOGGER.debug("REST request to get all results");
        return modelRatingResultsDataService.findTop50();
    }
    
    /**
     * GET  /modelRatingResults -> get results by params (processId/fromDate/toDate/modelType).
     */
    @RequestMapping(value = "/modelRatingResults", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ModelRatingResults> getModelRatingResults(@RequestBody ModelRatingResultsDTO modelRating) {
        LOGGER.debug("REST request to get results of model rating");
        return modelRatingResultsDataService.getClaimModelRatingResults(modelRating.getProcessId(), modelRating.getFromDate(), modelRating.getToDate(), modelRating.getModelType());
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
