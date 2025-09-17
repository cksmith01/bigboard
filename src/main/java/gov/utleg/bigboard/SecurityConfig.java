package gov.utleg.bigboard;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// if we're development, disable CORS
		if (Env.getEnv().isDev()) {
			// you have disable both or CORS will still be in effect
			http.cors(cors -> cors.disable());
			http.headers(header -> header.disable());
			logger.info("cors and headers disabled");
		}
		http.csrf(csrf -> csrf.disable());
		logger.info("csrf and headers disabled");

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/css", "/js", "/public", "/assets");
	}

}
