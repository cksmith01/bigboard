package gov.utleg.bigboard.exceptions;

public class ApiError {

	private String date;
	private String message;
	private String path;
	private int status;
	
	public ApiError(String date, String message, String path, int status) {
		super();
		this.date = date;
		this.message = message;
		this.path = path;
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ApiError [date=" + date + ", message=" + message + ", path=" + path + ", status=" + status + "]";
	}

}
