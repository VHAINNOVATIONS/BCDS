package gov.va.vba;

import org.junit.runner.RunWith;
import org.springframework.boot.test.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@Transactional
public abstract class AbstractIntegrationTest {
}
