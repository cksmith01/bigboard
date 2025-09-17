package gov.utleg.bigboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.utleg.bigboard.dao.ctables.StaffDao;
import gov.utleg.bigboard.model.Staff;

@Service
public class UserService {
	
	@Autowired
	StaffDao staffDao;
	
	public Staff getUserById(String userId) {
		return staffDao.getStaffById(userId);
	}

}
