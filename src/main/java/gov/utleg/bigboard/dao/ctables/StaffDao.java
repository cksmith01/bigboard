package gov.utleg.bigboard.dao.ctables;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import gov.utleg.bigboard.model.Staff;

@Mapper
public interface StaffDao {

	@Select(SELECT)
	List<Staff> getAllStaff();

	@Select(SELECT+" WHERE l.UserID = #{userid}")
	Staff getStaffById(@Param("userid") String userid);


	final String SELECT = 
	"""
	SELECT 
		l.UserID as UserID, 
		l.FullName as FullName, 
		l.Position as Position, 
		l.Office as Office, 
		l.EmailAddress as EmailAddress 
	FROM STAFF l
	""";

}
