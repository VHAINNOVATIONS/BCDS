package gov.va.vba.service.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.emory.mathcs.backport.java.util.Arrays;
import gov.va.vba.domain.ModelRatingResults;
import gov.va.vba.persistence.entity.RatingDecision;
import gov.va.vba.persistence.repository.ModelRatingResultsRepository;
import gov.va.vba.persistence.repository.RatingDecisionRepository;
import gov.va.vba.persistence.util.KneeCalculator;
import gov.va.vba.service.orika.ModelRatingResultsMapper;
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
	private ModelRatingResultsMapper modelRatingResultsMapper;
	
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
}
