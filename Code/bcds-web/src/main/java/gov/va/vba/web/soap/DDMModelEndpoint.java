package gov.va.vba.web.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import gov.va.vba.bcdss.models.DDMModelRequest;
import gov.va.vba.bcdss.models.DDMModelResponse;


@Endpoint
public class DDMModelEndpoint {
	
	private static final String NAMESPACE_URI = "http://bcdss.vba.va.gov/models";
	private static final Logger LOGGER = LoggerFactory.getLogger(DDMModelEndpoint.class);
	private DDMModelRepository dDMModelRepository;
	
	@Autowired
	public DDMModelEndpoint(DDMModelRepository dDMModelRepository) {
		this.dDMModelRepository = dDMModelRepository;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "dDMModelRequest")
	@ResponsePayload
	public DDMModelResponse dDMModel(@RequestPayload DDMModelRequest request) {
		LOGGER.debug("SOAP request to get a DDMModel");
		DDMModelResponse response = dDMModelRepository.findDDMModelResponse(request.getClaimantAge());
		return response;
	}

}
