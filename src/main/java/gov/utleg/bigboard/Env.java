package gov.utleg.bigboard;

import java.util.Map;
//import java.util.logging.Logger;

import gov.utleg.EnvCheck;
import gov.utleg.bigboard.util.Strings;

public class Env {

//	private static Logger logger = Logger.getLogger(Env.class.getName());
	private static Env env;

	private static String server = "?";
	private static String environment = "?";
	private static String dbUrl = "?";
	private static String dbUrlSource = "?";

	private Env() {
	}

	public static Env getEnv() {
		if (env == null) {
			env = new Env();
			String _environment = EnvCheck.getEnv();
//			logger.info("EnvCheck.getEnv=" + _environment); 
//			logger.info("EnvCheck.getDatabaseUrl=" + EnvCheck.getDatabaseUrl()); 

			if (Strings.notBlank(_environment)) {

				environment = _environment.toLowerCase();
				String _dbUrl = EnvCheck.getDatabaseUrl();
				if (Strings.notBlank(_dbUrl)) {
					dbUrl = _dbUrl;
					dbUrlSource = "EnvCheck";
				}

			}
			if (dbUrl.equals("?")) {

				Map<String, String> eMap = System.getenv();

				_environment = eMap.get("environment");
				String _dbUrl = eMap.get("dbString");

//				logger.info("System.getProperty(environment)=" + _environment);
//				logger.info("System.getProperty(dbString)=" + _dbUrl);

				if (Strings.notBlank(_environment)) {
					environment = _environment;
//					logger.warning("No environment variable found, defaulting to: " + environment + " <-----------");
				} else {
					environment = "dev?";
//					logger.warning("No environment variable found, defaulting to: " + environment + " <-----------");
				}

				if (Strings.notBlank(_dbUrl)) {
					dbUrl = _dbUrl;
					dbUrlSource = "System.getenv";
				}

			}

		}
		return env;
	}

	public void setServer(String s) {
		server = s;
	}

	public String getServer() {
		return server;
	}

	public String getEnvironment() {
		return environment;
	}

	public boolean isDev() {
		if (environment.startsWith("dev"))
			return true;
		return false;
	}

	public boolean isTest() {
		if (environment.startsWith("test"))
			return true;
		return false;
	}

	public boolean isProd() {
		if (environment.startsWith("prod"))
			return true;
		return false;
	}

	public String getDatabaseUrl() {
		return dbUrl;
	}

	public static String getDbUrlSource() {
		return dbUrlSource;
	}

}
