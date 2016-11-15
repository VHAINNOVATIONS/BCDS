package gov.va.vba.web.rest;

import java.util.List;

import javax.inject.Inject;

import gov.va.vba.web.rest.dto.ClaimDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/claims", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Claim> getClaims(@RequestBody ClaimDTO claim) {
        LOGGER.debug("REST request to get first few Claims");
        return claimDataService.findClaims(claim.isEstablishedDate(), claim.getFromDate(), claim.getToDate(), claim.getContentionType(), claim.getRegionalOfficeNumber());
    }

	@RequestMapping(value = "/claims/{veteranId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Claim> getClaims(@PathVariable Long veteranId) {
		LOGGER.debug("REST request to get first few Claims");
		return claimDataService.findByVeteranId(veteranId);
	}

    @RequestMapping(value = "/claims/{claimId}/veteran/{veteranId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Claim> getAggrigatedContentions(@PathVariable Long claimId, @PathVariable Long veteranId) {
        LOGGER.debug("REST request to get Aggregated contentions");
        return claimDataService.calculateContentions(claimId, veteranId);
    }

}
