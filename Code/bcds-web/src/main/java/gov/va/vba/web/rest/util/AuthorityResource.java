package gov.va.vba.web.rest.util;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.va.vba.persistence.entity.Authority;
import gov.va.vba.persistence.repository.AuthorityRepository;

@RestController
@RequestMapping(value = "/api")
public class AuthorityResource {

	@Inject
	AuthorityRepository authorityRepository;

	@RequestMapping(value = "/authorities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Authority>> getAuthorities() {
		return new ResponseEntity<>(authorityRepository.findAll(), HttpStatus.OK);
	}

}
