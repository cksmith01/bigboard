package gov.utleg.bigboard;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import gov.utleg.bigboard.util.Dates;

/**
 * A singleton used to track 
 * 	1. who has logged on since the application has been running
 *  2. the last time the caches were cleared
 *  
 * @author csmith
 */
public class UsageInfo {
	
	private static UsageInfo thereCanBeOnlyOne;
	
	private Map<String, String> users = new TreeMap<String, String>();
	private Map<String, String> userTag = new TreeMap<String, String>();
	private Date cacheCleardDate = null;
	
	private UsageInfo() {}
	
    public static UsageInfo getInstance() {
    	if (thereCanBeOnlyOne == null) {
    		thereCanBeOnlyOne = new UsageInfo();
    	}
        return thereCanBeOnlyOne;
    }
    
    public void addUser(String ipAddress) {
		users.put(ipAddress, Dates.format(null, "yyyy-MM-dd h:mm a"));
    }
    
    public Map<String, String> getUsers() {
    	Map<String, String> userMap = new TreeMap<String, String>();
    	for (String key : users.keySet()) {
    		String value = users.get(key);
			if (userTag.containsKey(key)) {
				String _value = value += " ["+userTag.get(key)+"]";
				userMap.put(key, _value);
			} else {
				userMap.put(key, value);
			}
		}
    	return userMap;
    }
    
    public void addCacheCleardDate(Date clearDate) {
    	cacheCleardDate = clearDate;
    }
    
    public Date getCacheCleardDate() {
    	return cacheCleardDate;
    }
    
    public void addTag(String ipAddress, String tag) {
    	userTag.put(ipAddress, tag);
    	
    }

}