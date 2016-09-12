package gov.va.vba.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    private AuthoritiesConstants() {
    }

    public static final String ADMIN = "ROLE_ADMIN";
    
    public static final String RATER = "ROLE_RATER";
    
    public static final String MODELER = "ROLE_MODELER";

    public static final String ROLE_MODEL_EXECUTOR = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
}
