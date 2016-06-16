package gov.va.vba.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import gov.va.vba.config.Constants;
import gov.va.vba.persistence.aop.logging.LoggingAspect;
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(Constants.SPRING_PROFILE_LOCAL)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
