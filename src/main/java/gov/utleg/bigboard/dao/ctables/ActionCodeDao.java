package gov.utleg.bigboard.dao.ctables;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import gov.utleg.bigboard.Constants;
import gov.utleg.bigboard.model.ActionCode;
import gov.utleg.bigboard.model.ActionOwnerCode;

@Mapper
public interface ActionCodeDao {

	@Cacheable(Constants.CACHE_ACTION_CODES)
	@Select(SELECT + " WHERE IsActive=1 AND DeleteFlag=0 AND ActionCode IN (SELECT distinct(ActionCode) FROM Sessions.dbo.ActionHistory WHERE SessionID like '${year}%') ORDER BY Description")
	List<ActionCode> getActiveActionCodes(@Param("year") Integer year);

	final String SELECT = 
	"""
	SELECT 
		a.Description as Description, 
		a.ActionCode as ActionCode
	FROM ActionMaster a
	""";
	
	
	
	public static final String COMBINED_ACTION_AND_OWNER_CODES = """
			SELECT ActionCode AS code, [Description], 'action' AS [type] FROM Ctables.dbo.ActionMaster WHERE IsActive=1 AND DeleteFlag=0 
			UNION
			SELECT OwnerID AS code, [Description], 'owner' AS [type] FROM Ctables.dbo.OwnerMaster WHERE [Type] != 'OLD' and [Type] NOT IN ('BBA','ICM','TSK')	
			""";
	@Cacheable(Constants.CACHE_ACTION_CODES)
	@Select(COMBINED_ACTION_AND_OWNER_CODES)
	List<ActionOwnerCode> getActionAndOwnerCodes();


}
