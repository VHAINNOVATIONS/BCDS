package gov.va.vba.web.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import gov.va.vba.bcdss.models.GetDDMModelRequest;
import gov.va.vba.bcdss.models.GetDDMModelResponse;

@Endpoint
public class ModelEndpoint {

	private static final String NAMESPACE_URI = "http://va.gov/vba/bcdss/models";
	private static final Logger LOGGER = LoggerFactory.getLogger(ModelEndpoint.class);
	private ModelRepository dDMModelRepository;

	@Autowired
	public ModelEndpoint(ModelRepository dDMModelRepository) {
		this.dDMModelRepository = dDMModelRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDDMModelRequest")
	@ResponsePayload
	public GetDDMModelResponse dDMModel(@RequestPayload GetDDMModelRequest request) {
		LOGGER.debug("SOAP request to get a DDMModel");
		return dDMModelRepository.findDDMModelResponse(request.getClaimantAge());
	}

}
