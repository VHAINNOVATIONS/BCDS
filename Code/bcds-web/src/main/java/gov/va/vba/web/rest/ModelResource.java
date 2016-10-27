package gov.va.vba.web.rest;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import gov.va.vba.bcdss.models.GetDdmModelRequest;
import gov.va.vba.bcdss.models.GetDdmModelResponse;
import gov.va.vba.web.utility.SoapClientUtility;

@RestController
@RequestMapping("/api")
public class ModelResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelResource.class);
	@Inject SoapClientUtility soapClientUtility;
	
	@RequestMapping(value = "/models", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public GetDdmModelResponse getDdmModel(@RequestBody GetDdmModelRequest getDdmModelRequest) {
		
		LOGGER.debug("Rest request to obtain DDM model : {}", getDdmModelRequest);
		return soapClientUtility.ddmModelRequest(getDdmModelRequest);
	}
}
