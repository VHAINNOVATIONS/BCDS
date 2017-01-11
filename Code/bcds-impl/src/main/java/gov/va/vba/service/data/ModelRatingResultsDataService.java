package gov.va.vba.service.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.emory.mathcs.backport.java.util.Arrays;
import gov.va.vba.domain.ModelRatingResults;
import gov.va.vba.domain.ModelRatingResultsDiag;
import gov.va.vba.domain.ModelRatingResultsStatus;
import gov.va.vba.domain.ModelRatingPattern;
import gov.va.vba.persistence.entity.DDMModelPatternIndex;
import gov.va.vba.persistence.entity.RatingDecision;
import gov.va.vba.persistence.repository.ModelRatingResultsRepository;
import gov.va.vba.persistence.repository.DDMModelPatternIndexRepository;
import gov.va.vba.persistence.repository.RatingDecisionRepository;
import gov.va.vba.persistence.util.KneeCalculator;
import gov.va.vba.service.orika.ModelRatingResultsMapper;
import gov.va.vba.service.orika.ModelRatingResultsDiagMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ModelRatingResultsDataService extends AbsDataService<gov.va.vba.persistence.entity.ModelRatingResults, ModelRatingResults> {
	
	private static final Logger LOG = LoggerFactory.getLogger(ModelRatingResultsDataService.class);
	
	@Autowired
	private ModelRatingResultsRepository modelRatingResultsRepository;
	
	@Autowired
	private DDMModelPatternIndexRepository ddmModelPatternIndexRepository;
	
	@Autowired
	private ModelRatingResultsMapper modelRatingResultsMapper;
	
	@Autowired
	private ModelRatingResultsDiagMapper modelRatingResultsDiagMapper;
	
	public ModelRatingResultsDataService() {
		this.setClasses(gov.va.vba.persistence.entity.ModelRatingResults.class, ModelRatingResults.class);
	}
	
	public List<ModelRatingResults> findTop50() {
        List<gov.va.vba.persistence.entity.ModelRatingResults> input = ((ModelRatingResultsRepository) repository).findTop50();
        List<ModelRatingResults> modelRatingResults = modelRatingResultsMapper.mapCollection(input);
        return modelRatingResults;
    }

	public List<ModelRatingResults> getClaimModelRatingResults(List<Long> processIds, Date fromDate, Date toDate, String modelType) {
		List<gov.va.vba.persistence.entity.ModelRatingResults> result = null;
		if(fromDate == null && toDate == null && processIds != null){
			result = modelRatingResultsRepository.findResultWithProcessIds(processIds);
		}
		else {
			long processId = (processIds == null) ?  0 : processIds.get(0);
			result = (processId == 0)
				? modelRatingResultsRepository.findResultByRangeOnProcssedDate(fromDate, toDate, modelType)
				: modelRatingResultsRepository.findResultByRangeOnProcssedDateAndProcessId(processId, fromDate, toDate, modelType);
		}
		
		List<ModelRatingResults> modelRatingResults = modelRatingResultsMapper.mapCollection(result);
		return modelRatingResults;
	}
	
	public List<ModelRatingResultsDiag> findDiagnosticCodes(List<Long> processIds) {
		if(processIds == null) return null;
		List<gov.va.vba.persistence.entity.ModelRatingResultsDiag> codes = modelRatingResultsRepository.findDiagonsticCodesByProcessIds(processIds);
		List<ModelRatingResultsDiag> diagCodes = modelRatingResultsDiagMapper.mapCollection(codes);
		LOG.debug("diagCodes" + diagCodes);
		return diagCodes;
	}
	
	public List<ModelRatingResultsStatus> findModelRatingResultStatusByProcessIds(List<Long> processIds) {
		if(processIds == null) return null;
		List<gov.va.vba.persistence.entity.ModelRatingResultsStatus> statuses = modelRatingResultsRepository.findModelRatingResultStatusByProcessIds(processIds);
		List<ModelRatingResultsStatus> resultsStatus = modelRatingResultsMapper.mapResultStatusCollection(statuses);
		LOG.debug("resultsStatus" + resultsStatus);
		return resultsStatus;
	}
	
	public List<Long> updateModelRatingResultsStatus(List<String> resultsStatus, String userId) {
		if(resultsStatus == null) return null;
		List<Long> processIds = new ArrayList<Long>();
		for (String status : resultsStatus) {
			String[] parts = status.split("-");
			String decision = parts[0];
			Long pid = Long.parseLong(parts[1].toString());
			Integer result = modelRatingResultsRepository.updateModelRatingResultStatusByProcessId(pid, decision, userId, new Date());
			if(result > 0) {
				processIds.add(pid);
			}
			LOG.debug("processIds Status" + processIds);
		}
		return processIds;
	}
	
	public List<Long> getProcessIdsFromRatingResults(List<ModelRatingResults> results){
		if(results == null) return null;
		List<Long> processIds = new ArrayList<Long>();
		for (ModelRatingResults ratingResult : results) {
			processIds.add(ratingResult.getProcessId());
		}
		
		return processIds;
	}
	
	public List<ModelRatingPattern> findModelRatingPatternInfo(Long patternId) {
		if(patternId == null) return null;
		List<gov.va.vba.persistence.entity.DDMModelPatternIndex> results = modelRatingResultsRepository.findModelRatingPatternInfo(patternId);
		List<ModelRatingPattern> patternInfo = modelRatingResultsMapper.mapModelRatingPatternCollection(results);
		return patternInfo;
	}
	
	public List<ModelRatingPattern> updateModelRatingPatternInfo(ModelRatingPattern patternData) {
		if(patternData == null) return null;
		modelRatingResultsRepository.createModelRatingPatternCDD(patternData.getPatternIndex().getPatternId(), 
																patternData.getPatternIndex().getAccuracy(),
																patternData.getPatternIndex().getCDD(), 
																patternData.getPatternIndex().getPatternIndexNumber(), patternData.getCreatedBy(), new Date(),
																patternData.getCategoryId(),patternData.getPatternIndex().getModelType());
		LOG.info("SAVED MODEL PATTERN INDEX SUCCESSFULLY");

		List<gov.va.vba.persistence.entity.DDMModelPatternIndex> results = modelRatingResultsRepository.findModelRatingPatternInfo(patternData.getPatternIndex().getPatternId());
		List<ModelRatingPattern> patternInfo = modelRatingResultsMapper.mapModelRatingPatternCollection(results);
		return patternInfo;
	}
}
