package gov.va.vba.web.soap;

import gov.va.vba.bcdss.models.*;
import gov.va.vba.domain.CustomBCDSSException;
import gov.va.vba.persistence.entity.EditModelPatternResults;
import gov.va.vba.service.data.ClaimDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Date;
import java.util.List;

@Endpoint
public class BcdsModelingEndpoint {

	private static final String NAMESPACE_URI = "http://va.gov/vba/bcdss/models";
	private static final Logger LOGGER = LoggerFactory.getLogger(BcdsModelingEndpoint.class);
	private ModelRepository dDMModelRepository;

    @Autowired
    private ClaimDataService claimDataService;

	@Autowired
	public BcdsModelingEndpoint(ModelRepository dDMModelRepository) {
		this.dDMModelRepository = dDMModelRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDdmModelRequest")
	@ResponsePayload
	public GetDdmModelResponse getDdmModel(@RequestPayload GetDdmModelRequest request) {
		LOGGER.debug("SOAP request to get a DDMModel... ...");
		return dDMModelRepository.findDdmModelResponse(request.getClaimantAge());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProcessClaimRequest")
	@ResponsePayload
	public GetProcessClaimResponse getProcessClaim(@RequestPayload GetProcessClaimRequest request) throws CustomBCDSSException {
		LOGGER.debug("SOAP request to get a Process Claim... ...");
        GetProcessClaimResponse getProcessClaimResponse = new GetProcessClaimResponse();
        try{
	        if(CollectionUtils.isNotEmpty(request.getVeteranClaimInput())) {
	            List<VeteranClaimRating> veteranClaimRatings = claimDataService.findByVeteranId(request.getVeteranClaimInput());
	            if(CollectionUtils.isNotEmpty(veteranClaimRatings)) {
	                getProcessClaimResponse.getVeteranClaimRatingOutput().addAll(veteranClaimRatings);
	            }else{
                	throw new CustomBCDSSException("No valid Pattern found for the data selected");
                }
	        }
        }catch(CustomBCDSSException e){
        	LOGGER.info("Exception Caught :::::: " + e);
        	e.getMessage();
        	e.printStackTrace();
        }
        return getProcessClaimResponse;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCurrentRatingRequest")
	@ResponsePayload
	public GetCurrentRatingResponse getCurrentRating(@RequestPayload GetCurrentRatingRequest request) {
		LOGGER.debug("SOAP request to get a Current Rating... ...");
		GetCurrentRatingResponse getCurrentRatingResponse = new GetCurrentRatingResponse();
		return getCurrentRatingResponse;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "editModelRequest")
	@ResponsePayload
	public EditModelResponse editModel(@RequestPayload EditModelRequest request) throws CustomBCDSSException{
		LOGGER.debug("SOAP request to edit model... ...");
		EditModelResponse editModelResponse = new EditModelResponse();
		EditModelPatternResults editModelPatternInfo = null;
		String editModel ="";
		String createdBy="";
		Long patternId = Long.valueOf(request.getIn().getModelPatternIndex().getPatternId());
		Long cdd= Long.valueOf(request.getIn().getModelPatternIndex().getCdd());
		createdBy = request.getIn().getModelPatternIndex().getCreatedBy();
		if(null==createdBy || "".equals(createdBy)){
			createdBy = "Modeler Service";
		}
		editModelPatternInfo = claimDataService.findModelRatingPatternInfo(patternId);
		if(null!=editModelPatternInfo.getPatternId()){
		 if(null!=editModelPatternInfo && editModelPatternInfo.getCDD()!=cdd){
			 editModel = claimDataService.updateModelRatingPatternInfo(editModelPatternInfo.getPatternId(),editModelPatternInfo.getAccuracy(), cdd, editModelPatternInfo.getPatternIndexNumber(),
					 																		createdBy, new Date(),
					 																		editModelPatternInfo.getCategoryId(), editModelPatternInfo.getModelType());
		 }else{
			 editModel = "CDD is not updated. No row inserted.";
		 }
		}else{
			editModel = "Pattern ID is not found" ;
		}
		editModelResponse.setOut(editModel);
		return editModelResponse;
	}

}
