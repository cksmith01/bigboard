package gov.utleg.bigboard.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.utleg.bigboard.Env;
import gov.utleg.bigboard.dao.ctables.QueryDao;
import gov.utleg.bigboard.dao.ctables.StaffDao;
import gov.utleg.bigboard.model.Staff;
import gov.utleg.bigboard.service.TestService;
import gov.utleg.bigboard.util.Email;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
	
	@Autowired
	StaffDao staffDao;
	
	@Autowired
	TestService testService;

	@Autowired
	Email email;
	
	@Autowired
	QueryDao queryDao;
	
	@GetMapping("/")
	public String[] getOptions() {
		
		return new String[] { "/currentUser", "/email",
				"Environment: " + Env.getEnv().getEnvironment(), "Server: " + Env.getEnv().getServer(),
				"dbUrl: " + Env.getEnv().getDatabaseUrl(), "dbUrlSource: " + Env.getDbUrlSource() };
		
	}

	
	@GetMapping("/currentUser")
	public Staff currentUser() {
		Staff user = super.getUser();
		if (user != null) return user;
		throw new RuntimeException("user object not available");
	}
	
	@GetMapping("/email")
	public String email() {

		if (email.send("chadsmith@le.utah.gov", "slc_utes@yahoo.com", "test email",
				"test message from: " + this.getClass().getName())) {
			return "message sent";
		} else {
			return "message send failed";
		}
	}
	
	@GetMapping("/groups")
	public String getGroups(HttpServletResponse resp) throws IOException {
		List<String> list = queryDao.getGroups();
		
		StringBuilder sb = new StringBuilder();
		
		Map<String, String> map = new TreeMap<String, String>();
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String groupList = iterator.next();
			StringTokenizer st = new StringTokenizer(groupList, "|");
			while (st.hasMoreTokens()) {  
		         String group = st.nextToken();
		         group = group.substring(0, group.indexOf(','));
		         if (!map.containsKey(group)) {
		        	 map.put(group, group);
					sb.append(group+"\n");
					List<String> users = queryDao.getUsersForGroup(group);
					if (users.size() == 0) {
						System.out.println("- no users in group");
						sb.append("- no users in group\n");
					}
					for (Iterator<String> iterator2 = users.iterator(); iterator2.hasNext();) {
						String user = iterator2.next();
						System.out.println("- " + user);
						sb.append("- " + user + "\n");
					}
					sb.append("\n");
		         }
		     }  
			
		}
		
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().write("<html><pre>"+sb.toString()+"</pre></html>");
		resp.getWriter().flush();

		return null;
	}
	
//	@GetMapping("/commBills")
//	public List<String> getCommBills() {
//		return queryDao.getCommBills(2023);
//	}

}
