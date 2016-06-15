package gov.va.vba;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.core.env.SimpleCommandLinePropertySource;

/**
 * Base application config for testing
 */
@EnableAutoConfiguration(exclude = {
        MetricFilterAutoConfiguration.class,
        MetricRepositoryAutoConfiguration.class,
        VelocityAutoConfiguration.class,
        MailSenderAutoConfiguration.class,
        JmxAutoConfiguration.class,
        ThymeleafAutoConfiguration.class
})
public class BaseTestApplication extends BaseApplication {
    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(Application.class);
        app.setShowBanner(false);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        addDefaultProfile(app, source);
    }
}
