package gov.va.vba.service.util;

import gov.va.vba.AbstractIntegrationTest;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

public class CamelCaserTest extends AbstractIntegrationTest {

    @Inject
    private CamelCaser camelCaser;

    @Test
    public void testToCamelCase() throws Exception {
        assertThat(camelCaser.toCamelCase("")).isNull();
        assertThat(camelCaser.toCamelCase("this is some string")).isEqualTo("thisIsSomeString");
        assertThat(camelCaser.toCamelCase("     ")).isNull();
    }
}