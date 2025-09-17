package gov.utleg.bigboard.filter;

import java.io.IOException;
//import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import gov.utleg.bigboard.Env;
import gov.utleg.bigboard.UsageInfo;
import gov.utleg.bigboard.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {
	
	@Autowired
	UserService userService;
	
//	private Logger logger = Logger.getLogger(getClass().getName());
	public static final int APP_ID = 678; // <---- THIS IS NOT CORRECT!!! WE NEED A NEW APP ID FOR THIS APP
	
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		
		Env.getEnv().setServer(req.getServerName());
		
//		logger.info("URI["+req.getRequestURI()+"]");
//		System.out.println("BigBoard: URI[" + req.getRequestURI() + "]" + "[" + req.getQueryString() + "]");
		
		/*
		Staff user = null;
		
		String server = request.getServerName();
		// IF WE'RE RUNNING LOCALLY, BYPASS AUTHENTICATION...
		if (server.indexOf("localhost") == -1) {
			
			LeAuth2 auth = new LeAuth2(APP_ID,request,response, "J0K77oqIlneVjQCivii5a4MIQbFaCzBy");
			String redirect = auth.getRedir();
			if (Strings.isBlank(redirect)) {
				logger.info("Authenticated user[" + auth.getLogin() + "] context[" + auth.getContext() + "] appID["
						+ APP_ID + "] URI[" + request.getRequestURI() + "]" + "] Role[" + auth.getRoles() + "]");
				
				user = userService.getUserById(auth.getLogin());
				String role = "?";
				if (auth.getRoles() != null && auth.getRoles().size() > 0) {
					role = auth.getRoles().get(0);
				}
				user.setRole(role);
				
			} else {
				logger.info("Authentication failed, redirecting to [" + redirect + "] ...");
				response.sendRedirect(redirect);
			}
			
		} else {
			
			// AND LOAD A DEFAULT USER...
			logger.warning("Authentication bypassed (running on localhost)");
			user = userService.getUserById("chadsmith");	// <--- load a default user
			user.setRole("ROLE_USER");
			
		}
		
		// add to springs security context so the application can apply security as needed...
		SecurityContext sc = SecurityContextHolder.getContext();
		UserPrincipal principal = new UserPrincipal(user);
		sc.setAuthentication(principal);
		
		
		*/
		
		UsageInfo.getInstance().addUser(req.getRemoteAddr());
		
		filterChain.doFilter(req, res);
	}

}
