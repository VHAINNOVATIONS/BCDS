package gov.va.vba.web.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import gov.va.vba.bcdss.models.EditModelRequest;
import gov.va.vba.bcdss.models.EditModelResponse;
import gov.va.vba.bcdss.models.GetCurrentRatingRequest;
import gov.va.vba.bcdss.models.GetCurrentRatingResponse;
import gov.va.vba.bcdss.models.GetDdmModelRequest;
import gov.va.vba.bcdss.models.GetDdmModelResponse;
import gov.va.vba.bcdss.models.GetProcessClaimRequest;
import gov.va.vba.bcdss.models.GetProcessClaimResponse;

@Endpoint
public class BcdsModelingEndpoint {

	private static final String NAMESPACE_URI = "http://va.gov/vba/bcdss/models";
	private static final Logger LOGGER = LoggerFactory.getLogger(BcdsModelingEndpoint.class);
	private ModelRepository dDMModelRepository;

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
	public GetProcessClaimResponse getProcessClaim(@RequestPayload GetProcessClaimRequest request) {
		LOGGER.debug("SOAP request to get a Process Claim... ...");
		GetProcessClaimResponse getProcessClaimResponse = new GetProcessClaimResponse();
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
	public EditModelResponse editModel(@RequestPayload EditModelRequest request) {
		LOGGER.debug("SOAP request to edit model... ...");
		EditModelResponse editModelResponse = new EditModelResponse();
		return editModelResponse;
	}

}
