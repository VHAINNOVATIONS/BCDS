package gov.va.vba.service.util;

import org.junit.Test;

import java.lang.reflect.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomUtilTest {

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<RandomUtil> constructor = RandomUtil.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testGeneratePassword() throws Exception {
        assertGeneratedString(RandomUtil.generatePassword());
    }

    @Test
    public void testGenerateActivationKey() throws Exception {
        assertGeneratedString(RandomUtil.generateActivationKey());
    }

    @Test
    public void testGenerateResetKey() throws Exception {
        assertGeneratedString(RandomUtil.generateResetKey());
    }

    private void assertGeneratedString(String password){
        assertThat(password).isNotNull();
        assertThat(password).hasSize(20);
    }
}