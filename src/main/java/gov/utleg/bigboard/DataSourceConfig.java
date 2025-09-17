package gov.utleg.bigboard;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import gov.utleg.bigboard.util.Strings;

@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfig {

	private static Logger logger = Logger.getLogger(DataSourceConfig.class.getName());

	@Value("${database.url}")
	protected String sDatabaseUrl;

	protected Env env;

	protected void init() {

		if (env == null) {
//			logger.info("Spring (application.properties) database URL: " + sDatabaseUrl); 

			env = Env.getEnv();
			String eDatabaseUrl = env.getDatabaseUrl();
			
//			System.out.println("-------------");
//			System.out.println("getDatabaseUrl="+env.getDatabaseUrl());
//			System.out.println("getEnvironment="+env.getEnvironment());
//			System.out.println("getServer="+env.getServer());
//			System.out.println("getDbUrlSource="+env.getDbUrlSource());
//			System.out.println("-------------");

			if (Strings.notBlank(eDatabaseUrl)) {
				this.sDatabaseUrl = eDatabaseUrl;
//				logger.info("Environment database URL found, overriding spring URL: " + sDatabaseUrl + "<---");
//				logger.info("sDatabaseUrl[" + sDatabaseUrl + "]");
//				logger.info("eDatabaseUrl[" + eDatabaseUrl + "]");
			} else {
				logger.warning("No environment database URL found, using spring URL (see application.properties)");
			}
		}
		
		// TODO: naughty...
//		sDatabaseUrl = "jdbc:jtds:sqlserver://utlegsql.leg.local";
		
	}

}