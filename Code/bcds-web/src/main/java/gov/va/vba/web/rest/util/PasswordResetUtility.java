package gov.va.vba.web.rest.util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.va.vba.service.util.HashingUtility;

@RestController
@RequestMapping(value = "/passwordUtility")
public class PasswordResetUtility {

	@Inject
	HashingUtility hashingUtility;

	@RequestMapping(value = "/resetPassword/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> resetPassword(@PathVariable String login) {
		List<String> response = new ArrayList<>();
		response.add(hashingUtility.generateHash(login + "1#"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
