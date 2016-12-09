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
        List<ModelRatingResults> output = new ArrayList<>();
        List<gov.va.vba.persistence.entity.ModelRatingResults> input = ((ModelRatingResultsRepository) repository).findTop50();
        mapper.mapAsCollection(input, output, outputClass);
        return output;
    }

	public List<ModelRatingResults> getClaimModelRatingResults(Long processId, Date fromDate, Date toDate, String modelType) {
		if (fromDate == null) {
			Calendar instance = Calendar.getInstance();
			instance.set(1900, 12, 31);
			fromDate = instance.getTime();
		}
		if (toDate == null) {
			toDate = new Date();
		}
		
		List<gov.va.vba.persistence.entity.ModelRatingResults> result = (fromDate == null && toDate == null)
				?modelRatingResultsRepository.findOneResult(processId)
				:modelRatingResultsRepository.findResultByRangeOnProcssedDate(processId, fromDate, toDate, modelType);
		List<ModelRatingResults> output = new ArrayList<>();
		mapper.mapAsCollection(result, output, outputClass);
		return output;
	}
}
