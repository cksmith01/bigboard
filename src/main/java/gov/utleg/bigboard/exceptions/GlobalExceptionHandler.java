package gov.utleg.bigboard.exceptions;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import gov.utleg.bigboard.util.Dates;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
	
	private Logger log = Logger.getLogger(getClass().getName());
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex, WebRequest req) {
		ApiError error = 
				new ApiError(Dates.formatDB(null), ex.getMessage(), req.getDescription(false), ex.getStatusCode().value());
		log.warning(error.toString());
		return new ResponseEntity<ApiError>(error, ex.getStatusCode());
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<?> handleApiException(ApiException ex, WebRequest req) {
		ApiError error = 
				new ApiError(Dates.formatDB(null), ex.getMessage(), req.getDescription(false), ex.getStatus().value());
		log.warning(error.toString());
		return new ResponseEntity<ApiError>(error, ex.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest req) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ApiError error = 
				new ApiError(Dates.formatDB(null), ex.getMessage(), req.getDescription(false), status.value());
		log.severe(error.toString());
		ex.printStackTrace();
		return new ResponseEntity<ApiError>(error, status);
	}


}
