package gov.va.vba.web.soap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import gov.va.vba.bcdss.models.DdmPatternIndex;
import gov.va.vba.bcdss.models.GetDdmModelResponse;

@Component
public class ModelRepository {

	private GetDdmModelResponse dDMModelResponse;

	@PostConstruct
	public void initData() {
		DdmPatternIndex patternIndex = new DdmPatternIndex();
		patternIndex.setAccuracy(1);
		patternIndex.setCatelogId(2);
		patternIndex.setCdd(3);
		patternIndex.setCreatedBy("TESTER");
		// patternIndex.setCreatedDate(null);
		patternIndex.setNumberOfOccurances(4);
		patternIndex.setPatternId(5);

		dDMModelResponse = new GetDdmModelResponse();
		dDMModelResponse.setCcdAge(10);
		dDMModelResponse.setClaimantAge(20);
		dDMModelResponse.setContentionCount(40);
		dDMModelResponse.setModelType("TEST");
		dDMModelResponse.setPatternIndex(patternIndex);
		dDMModelResponse.setPattrenId(50);
		dDMModelResponse.setPriorCdd(60);

	}

	public GetDdmModelResponse findDdmModelResponse(int claimantAge) {
		return dDMModelResponse;
	}

}
