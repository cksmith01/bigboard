package gov.utleg.bigboard.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import gov.utleg.bigboard.Env;
import gov.utleg.bigboard.util.Dates;

@Component
public class TestJob {
	
	@Scheduled(cron = "* 00 16 30 * *") // <-- every day at 4:30pm
	public void run() {
		
		System.out.println(this.getClass().getName() + " called at " + Dates.formatFull(null));
		
		if (!Env.getEnv().isProd()) {
			// typically we don't want batch jobs running on non-production instances 
			return;
		}
		
	}
}
