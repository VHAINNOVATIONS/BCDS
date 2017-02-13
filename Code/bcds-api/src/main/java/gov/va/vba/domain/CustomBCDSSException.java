package gov.va.vba.domain;

public class CustomBCDSSException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CustomBCDSSException(){
	}
	
	public CustomBCDSSException(String message) {
		super(message);
	}
	
	public CustomBCDSSException (Throwable cause) {
        super (cause);
    }

    public CustomBCDSSException (String message, Throwable cause) {
        super (message, cause);
    }
}
