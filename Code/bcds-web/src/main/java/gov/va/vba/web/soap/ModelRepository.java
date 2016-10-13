package gov.va.vba.web.soap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import gov.va.vba.bcdss.models.DDMPatternIndex;
import gov.va.vba.bcdss.models.GetDDMModelResponse;

@Component
public class ModelRepository {

	private GetDDMModelResponse dDMModelResponse;

	@PostConstruct
	public void initData() {
		DDMPatternIndex patternIndex = new DDMPatternIndex();
		patternIndex.setAccuracy(1);
		patternIndex.setCatelogId(2);
		patternIndex.setCDD(3);
		patternIndex.setCreatedBy("TESTER");
		// patternIndex.setCreatedDate(null);
		patternIndex.setNumberOfOccurances(4);
		patternIndex.setPatternId(5);

		dDMModelResponse = new GetDDMModelResponse();
		dDMModelResponse.setCCDAge(10);
		dDMModelResponse.setClaimantAge(20);
		dDMModelResponse.setContentionCount(40);
		dDMModelResponse.setModelType("TEST");
		dDMModelResponse.setPatternIndex(patternIndex);
		dDMModelResponse.setPattrenId(50);
		dDMModelResponse.setPriorCDD(60);

	}

	public GetDDMModelResponse findDDMModelResponse(int claimantAge) {
		return dDMModelResponse;
	}

}
