package gov.va.vba.domain;

public class ErrorResponse {
	
	private String message;
	private int errorCode;

	public ErrorResponse() {
        super();
    }
	
	public ErrorResponse(String message) {
        this.message = message;
    }
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
