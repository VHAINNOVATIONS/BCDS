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
import gov.va.vba.domain.ModelRatingResultsDiag;
import gov.va.vba.domain.util.ModelPatternIndex;
import gov.va.vba.domain.ModelRatingDetailsResult;
import gov.va.vba.domain.ModelRatingPattern;
import gov.va.vba.service.data.ModelRatingResultsDataService;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class ModelResultsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelResultsResource.class);
    public static final String DetailedReport = "DETAILED";
    public static final String AggregateReport = "AGGREGATE";
    
    @Inject
	private ModelRatingResultsDataService modelRatingResultsDataService;
      
    /**
     * GET  /results -> get all results.
     */
    @RequestMapping(value = "/results",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ModelRatingDetailsResult getModelRatingResults(@RequestBody ModelRatingResultsDTO modelRating) {
        LOGGER.debug("REST request to get results of model rating");
        ModelRatingDetailsResult detailedResult = new ModelRatingDetailsResult();
        detailedResult.modelRatingResults = modelRatingResultsDataService.getClaimModelRatingResults(modelRating.getProcessIds(), modelRating.getFromDate(), modelRating.getToDate(), modelRating.getModelType());
        List<Long> processIds = (modelRating.getProcessIds() == null) ? modelRatingResultsDataService.getProcessIdsFromRatingResults(detailedResult.modelRatingResults) : modelRating.getProcessIds();
        if(processIds != null && processIds.size() > 0){
        	detailedResult.diagnosticCodes = modelRatingResultsDataService.findDiagnosticCodes(processIds);
        	detailedResult.resultsStatus = modelRatingResultsDataService.findModelRatingResultStatusByProcessIds(processIds);
        }
        return detailedResult;
    }
    
    /**
     * GET  /updateModelRatingResultsStatus -> get results by params (processId/decision).
     */
    @RequestMapping(value = "/updateModelRatingResultsStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ModelRatingDetailsResult updateModelRatingResultsStatus(@RequestBody ModelRatingResultsDTO modelRating) {
        LOGGER.debug("REST request to update status of model rating");
        ModelRatingDetailsResult detailedResult = new ModelRatingDetailsResult();
        List<Long> processIds = modelRatingResultsDataService.updateModelRatingResultsStatus(modelRating.getResultsStatus());
        if(processIds == null) return null;
        
        detailedResult.modelRatingResults = modelRatingResultsDataService.getClaimModelRatingResults(modelRating.getProcessIds(), null, null, null);
        detailedResult.diagnosticCodes = modelRatingResultsDataService.findDiagnosticCodes(modelRating.getProcessIds());
        detailedResult.resultsStatus = modelRatingResultsDataService.findModelRatingResultStatusByProcessIds(modelRating.getProcessIds());
        return detailedResult;
    }
    
    /**
     * GET  /getModelRatingPatternInfo -> get results by params (patternId).
     */
    @RequestMapping(value = "/modelRatingPatternInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ModelRatingPattern> getModelRatingPatternInfo(@RequestBody ModelRatingResultsDTO modelRating) {
        LOGGER.debug("REST request to get model rating patern info");
        List<ModelRatingPattern> result = modelRatingResultsDataService.findModelRatingPatternInfo(modelRating.getPatternId()); 
        return result;
    }
    
    /**
     * GET  /updateModelRatingPatternInfo -> get results by params (patternId).
     */
    @RequestMapping(value = "/updateModelRatingPatternInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ModelRatingPattern> updateModelRatingPatternInfo(@RequestBody ModelRatingResultsDTO modelRating) {
        LOGGER.debug("REST request to update model rating patern info");
        ModelRatingPattern patternInfo = new ModelRatingPattern();
        ModelPatternIndex patternIndex = new ModelPatternIndex();
        patternIndex.setPatternId(modelRating.getPatternId());
        patternIndex.setCDD(modelRating.getCDD());
        patternIndex.setPatternIndexNumber(modelRating.getPatternIndexNumber());
        patternIndex.setModelType(modelRating.getModelType());
        patternIndex.setAccuracy(modelRating.getAccuracy());
        patternInfo.setPatternIndex(patternIndex);
        patternInfo.setCategoryId(modelRating.getCategoryId());
        patternInfo.setCreatedBy("admin");
        
        List<ModelRatingPattern> result = modelRatingResultsDataService.updateModelRatingPatternInfo(patternInfo); 
        return result;
    }
    
    /**
     * GET  /modelRatingResultsReports -> get results by params (processId/fromDate/toDate/modelType/reportType).
     */
    @RequestMapping(value = "/modelRatingResultsReport", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ModelRatingDetailsResult getModelRatingResultsReport(@RequestBody ModelRatingResultsDTO modelRating) {
        LOGGER.debug("REST request to get report of results of model rating");
        ModelRatingDetailsResult detailedResult = new ModelRatingDetailsResult();
        switch(modelRating.getReportType().toUpperCase()){
        	case "DETAILED":
        		detailedResult.modelRatingResults = modelRatingResultsDataService.getClaimModelRatingResults(modelRating.getProcessIds(), modelRating.getFromDate(), modelRating.getToDate(), modelRating.getModelType());
     	        List<Long> processIds = (modelRating.getProcessIds() == null) ? modelRatingResultsDataService.getProcessIdsFromRatingResults(detailedResult.modelRatingResults) : modelRating.getProcessIds();
     	        if(processIds != null && processIds.size() > 0){
     	        	detailedResult.diagnosticCodes = modelRatingResultsDataService.findDiagnosticCodes(processIds);
     	        	detailedResult.resultsStatus = modelRatingResultsDataService.findModelRatingResultStatusByProcessIds(processIds);
     	        }
        		break;
        	case "AGGREGATE" :
        		break;
        }
        
        return detailedResult;
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
