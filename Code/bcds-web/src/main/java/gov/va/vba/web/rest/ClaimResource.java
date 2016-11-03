package gov.va.vba.web.rest;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
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

    @RequestMapping(value = "/claims/range", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Claim> getClaimsInRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
        LOGGER.debug("REST request to get first few Claims");
        return claimDataService.findClaimsInRanged(fromDate, toDate);
    }

	@RequestMapping(value = "/claims/{veteranId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Claim> getClaims(@PathVariable Long veteranId) {
		LOGGER.debug("REST request to get first few Claims");
		return claimDataService.findByVeteranId(veteranId);
	}

}
