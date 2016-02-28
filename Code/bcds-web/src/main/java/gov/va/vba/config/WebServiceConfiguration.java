package gov.va.vba.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Created by 582163 on 2/28/2016.
 */
@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(WebServiceConfiguration.class);

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        log.info("Registering message dispatcher servlet.");
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "ratings")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema ratingInformationServiceSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("RatingInformationServicePort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://services.rba.benefits.vba.va.gov/");
        wsdl11Definition.setSchema(ratingInformationServiceSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema ratingInformationServiceSchema() {
        return new SimpleXsdSchema(new ClassPathResource("wsdl/RatingInformationService.xsd"));
    }
}
