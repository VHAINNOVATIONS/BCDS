package gov.va.vba.web.soap;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import gov.va.vba.bcdss.models.DDMModelResponse;
import gov.va.vba.bcdss.models.DDMPatternIndex;


@Component
public class DDMModelRepository {
	
	private DDMModelResponse dDMModelResponse;

	@PostConstruct
	public void initData() {
		DDMPatternIndex patternIndex = new DDMPatternIndex();
		patternIndex.setAccuracy(1);
		patternIndex.setCatelogId(2);
		patternIndex.setCDD(3);
		patternIndex.setCreatedBy("TESTER");
		//patternIndex.setCreatedDate(null);
		patternIndex.setNumberOfOccurances(4);
		patternIndex.setPatternId(5);
		
		
		dDMModelResponse = new DDMModelResponse();
		dDMModelResponse.setCCDAge(10);
		dDMModelResponse.setClaimantAge(20);
		dDMModelResponse.setClaimCount_0020(30);
		dDMModelResponse.setContentionCount(40);
		dDMModelResponse.setModelType("TEST");
		dDMModelResponse.setPatternIndex(patternIndex);
		dDMModelResponse.setPattrenId(50);
		dDMModelResponse.setPriorCDD(60);
	}

	public DDMModelResponse findDDMModelResponse(int claimantAge) {
		return dDMModelResponse;
	}
	
	
	
	
	
	

}
