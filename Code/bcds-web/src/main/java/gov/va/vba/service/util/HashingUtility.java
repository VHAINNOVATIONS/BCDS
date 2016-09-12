package gov.va.vba.service.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class HashingUtility {

	public String generateHash(String text) {
		String newPasswordHash = BCrypt.hashpw(text, BCrypt.gensalt());
		return newPasswordHash;
	}

}
