package gov.va.vba.service.orika;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Mapped {
    boolean byDefault() default true;
}
