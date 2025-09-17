/**
 * ref: https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
 */

package gov.utleg.bigboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebAppInitializer extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebAppInitializer.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebAppInitializer.class);
	}
	
}