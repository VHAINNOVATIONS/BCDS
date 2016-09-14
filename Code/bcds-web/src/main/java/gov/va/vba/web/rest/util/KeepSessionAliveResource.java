package gov.va.vba.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/api")
public class KeepSessionAliveResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeepSessionAliveResource.class);

	@RequestMapping(value = "/keepsessionalive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> renewSession() {
		LOGGER.debug("REST request to renew session");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
