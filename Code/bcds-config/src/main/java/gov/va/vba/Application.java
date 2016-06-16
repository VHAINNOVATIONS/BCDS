package gov.va.vba;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.google.common.base.Joiner;

/**
 * Use start web application, extends base application config, adds liquibase setup and specifies exclusions from spring boot auto configuration
 * Velocity templates are used for Jhipster generation and therefore are not required for application startup.
 */
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class, VelocityAutoConfiguration.class})
public class Application extends BaseApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(Application.class);
        app.setShowBanner(false);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        addDefaultProfile(app, source);
        addLiquibaseScanPackages();
        Environment env = app.run(args).getEnvironment();
        LOGGER.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://127.0.0.1:{}\n\t" +
                        "External: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));

    }

    /**
     * Set the liquibases.scan.packages to avoid an exception from ServiceLocator.
     */
    private static void addLiquibaseScanPackages() {
        System.setProperty("liquibase.scan.packages", Joiner.on(",").join(
                "liquibase.change", "liquibase.database", "liquibase.parser",
                "liquibase.precondition", "liquibase.datatype",
                "liquibase.serializer", "liquibase.sqlgenerator", "liquibase.executor",
                "liquibase.snapshot", "liquibase.logging", "liquibase.diff",
                "liquibase.structure", "liquibase.structurecompare", "liquibase.lockservice",
                "liquibase.ext", "liquibase.changelog"));
    }

}
