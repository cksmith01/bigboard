package gov.utleg.bigboard.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.utleg.bigboard.UsageInfo;
import gov.utleg.bigboard.dao.ctables.QueryDao;
import gov.utleg.bigboard.model.SessionDate;
import gov.utleg.bigboard.model.SysUser;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/info")
public class InfoController extends BaseController {
	
	@Autowired
	QueryDao queryDao;

	@GetMapping("/users")
	public List<SysUser> getUsers() {
		List<SysUser> _list = new ArrayList<SysUser>();

		Map<String, String> list = UsageInfo.getInstance().getUsers();
		list.forEach((key, value) -> {
			SysUser su = new SysUser();
			su.setIp(key);
			su.setDate(value);
			_list.add(su);
		});

		return _list;

	}

	@GetMapping("/cacheClearDate")
	public Date getCacheClearDate() {
		Date clearDate = UsageInfo.getInstance().getCacheCleardDate();
		return clearDate;
	}

	@GetMapping("/tag/{tag}/")
	public String tag(HttpServletRequest request, @PathVariable String tag) {
		String userIp = request.getRemoteAddr();
		UsageInfo.getInstance().addTag(userIp, tag);
		
		return userIp + "[" + tag + "]";
	}
	
	@GetMapping("/session/dates/{year}")
	public List<SessionDate> sessionDates(@PathVariable Integer year) {
		return queryDao.getSessionStartAndEndDates(year);
	}

}
