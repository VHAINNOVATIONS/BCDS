package gov.va.vba.web.rest;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import gov.va.vba.domain.Claim;
import gov.va.vba.service.data.ClaimDataService;

@RestController
@RequestMapping("/api")
public class ClaimResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClaimResource.class);

	@Inject
	private ClaimDataService claimDataService;

	@RequestMapping(value = "/claims", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Claim> getFirstFewClaims() {
		LOGGER.debug("REST request to get first few Claims");
		return claimDataService.findFirstNumberedRow();
	}
	
	@RequestMapping(value = "/claims/{veteranId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Claim> getClaims(@PathVariable Long veteranId) {
		LOGGER.debug("REST request to get first few Claims");
		return claimDataService.findByVeteranId(veteranId);
	}

}
