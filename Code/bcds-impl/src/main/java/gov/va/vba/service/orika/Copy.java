package gov.va.vba.service.orika;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Pete Grazaitis on 11/9/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Copy {

    String value();

    String converterId() default "";

    boolean writeOnly() default false;
}
