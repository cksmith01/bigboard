package gov.utleg.bigboard.service;

import java.util.HashMap;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
//	@Autowired
//	StaffDao staffDao;
	
	public static void main(String[] args) {
		TestService.test();
	}

	public static void test() {

		String[] split = "16-10a-1420, 16-10a-1421, 16-6a-1410, 16-6a-1411, 59-1-403, 16-10a-1420, 16-10a-1421, 16-6a-1410, 16-6a-1411, 59-1-403".split(",");
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < split.length; i++) {
			map.put(split[i], split[i]);
		}
		
		System.out.println(map.values().toString()); //CKS: WIP
	}
	
}
