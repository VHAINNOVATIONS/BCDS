package gov.va.vba.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import gov.va.vba.persistence.entity.UserEditable;
import gov.va.vba.security.SecurityUtils;

@Service
public class UserUpdateService {

	public UserEditable prepareUpdate(UserEditable userEditable) {
		userEditable.setLastModifiedBy(SecurityUtils.getCurrentLogin());
		userEditable.setLastModifiedDate(DateTime.now());
		return userEditable;
	}
}
