package gov.utleg.bigboard.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import gov.utleg.bigboard.model.Staff;

@Component
public class BaseController {

	protected Staff getUser() {
		SecurityContext sc = SecurityContextHolder.getContext();
		Object userObj = sc.getAuthentication().getPrincipal();
		if (userObj != null)
			return (Staff) userObj;
		Staff staff = new Staff();
		staff.setUserID("na");
		staff.setFullName("user not available");
		return staff;
	}

}
