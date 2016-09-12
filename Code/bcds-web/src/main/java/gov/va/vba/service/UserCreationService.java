package gov.va.vba.service;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import gov.va.vba.persistence.entity.UserEditable;
import gov.va.vba.security.SecurityUtils;
import gov.va.vba.service.util.HashingUtility;

@Service
public class UserCreationService {
	
	@Inject
	HashingUtility hashingUtility;
	
	public UserEditable prepareCreation(UserEditable userEditable) {
		userEditable.setPassword(hashingUtility.generateHash(userEditable.getPassword()));
		userEditable.setCreatedBy(SecurityUtils.getCurrentLogin());
		userEditable.setCreatedDate(DateTime.now());
		return userEditable;
	}

}
