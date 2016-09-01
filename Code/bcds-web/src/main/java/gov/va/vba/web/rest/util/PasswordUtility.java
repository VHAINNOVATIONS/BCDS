package gov.va.vba.web.rest.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/passwordUtility")
public class PasswordUtility {

	@RequestMapping(value = "/encrypt/{plainText}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> encrypt(@PathVariable String plainText){
		List<String> response = new ArrayList<>();
		response.add(generateHash(plainText));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resetPassword/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> resetPassword(@PathVariable String login){
		List<String> response = new ArrayList<>();
		response.add(generateHash(login + "1#"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private String generateHash(String text) {
		String newPasswordHash = BCrypt.hashpw(text, BCrypt.gensalt());
		return newPasswordHash;
	}
}
