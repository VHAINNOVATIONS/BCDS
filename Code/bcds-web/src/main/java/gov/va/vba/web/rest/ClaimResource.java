
package gov.va.vba.web.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import gov.va.vba.bcdss.models.BcdsModelingPort;
import gov.va.vba.bcdss.models.BcdsModelingPortService;
import gov.va.vba.bcdss.models.ClaimRating;
import gov.va.vba.bcdss.models.GetProcessClaimRequest;
import gov.va.vba.bcdss.models.GetProcessClaimResponse;
import gov.va.vba.bcdss.models.Rating;
import gov.va.vba.bcdss.models.VeteranClaimRating;
import gov.va.vba.web.rest.dto.ClaimDTO;
import org.apache.commons.collections.CollectionUtils;
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
		List<Claim> claims = new ArrayList<>();
		claims= claimDataService.findFirstNumberedRow();
		LOGGER.info("SIZE :::: " + claims.size());
		 return claims;
	}

    @RequestMapping(value = "/claims", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Claim> getClaims(@RequestBody ClaimDTO claim) {
        LOGGER.debug("REST request to get first few Claims");

        String contentionTypeStr=null;
        Date fromDate = claim.getFromDate();
        Date toDate = claim.getToDate();
        Long regionalOffice = claim.getRegionalOfficeNumber();
        String contentionType = claim.getContentionType();
        StringBuilder sb = new StringBuilder();
        List<Claim> filteredClaims = new ArrayList<>();
        if(null!=contentionType && !"".equalsIgnoreCase(contentionType)){
        	sb.append("%").append(contentionType.toLowerCase()).append("%");
        	contentionTypeStr = sb.toString();
        }
       //Query Conditions
        if (fromDate==null && toDate==null && (contentionType==null || "".equals(contentionType) ) && regionalOffice==0) {
        	filteredClaims= claimDataService.findFirstNumberedRow();
        }else{
        	filteredClaims= claimDataService.findClaims(contentionTypeStr, regionalOffice, fromDate, toDate);
        }
        return filteredClaims;
    }

    @RequestMapping(value = "/claims/process", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public GetProcessClaimResponse getProcessClaims(@RequestBody GetProcessClaimRequest request) {
        LOGGER.debug("REST request to get first few Claims");
        BcdsModelingPort modelingService = new BcdsModelingPortService().getBcdsModelingPortSoap11();
        GetProcessClaimResponse processClaim = modelingService.getProcessClaim(request);
        LOGGER.info("***********************");
        LOGGER.info(processClaim.getVeteranClaimRatingOutput().toString());
        List<VeteranClaimRating> output = processClaim.getVeteranClaimRatingOutput();
        return processClaim;
    }

    @RequestMapping(value = "/claims/results", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Claim> getClaimResults(@RequestBody ClaimDTO claim) {
        LOGGER.debug("REST request to get results of process claims");
        return claimDataService.getProcessClaimsResults(claim.isEstablishedDate(), claim.getFromDate(), claim.getToDate(), claim.getContentionType(), claim.getRegionalOfficeNumber());
    }


    @RequestMapping(value = "/claims/{claimId}/veteran/{veteranId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Claim> getAggrigatedContentions(@PathVariable("claimId") Long claimId, @PathVariable("veteranId") Long veteranId) {
        LOGGER.debug("REST request to get Aggregated contentions");
        return claimDataService.calculateContentions(claimId, veteranId);
    }

}
