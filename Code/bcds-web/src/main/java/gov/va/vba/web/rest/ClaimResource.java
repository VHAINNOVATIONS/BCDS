package gov.va.vba.web.rest;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import gov.va.vba.persistence.entity.Claim;
import gov.va.vba.persistence.repository.ClaimRepository;

@RestController
@RequestMapping("/api")
public class ClaimResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClaimResource.class);

	@Inject
	private ClaimRepository claimRepository;

	@RequestMapping(value = "/claims", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Claim> getAll() {
		LOGGER.debug("REST request to get all Users");
		return claimRepository.findAll();
	}

}
