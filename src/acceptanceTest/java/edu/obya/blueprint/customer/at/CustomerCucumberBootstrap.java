package edu.obya.blueprint.customer.at;

import edu.obya.blueprint.ExceptionHandling;
import edu.obya.blueprint.WebSecurityConfiguration;
import edu.obya.blueprint.WebValidationConfiguration;
import edu.obya.blueprint.customer.application.CustomerApplicationConfiguration;
import edu.obya.blueprint.customer.domain.CustomerId;
import edu.obya.blueprint.customer.infra.data.jpa.CustomerJpaConfiguration;
import edu.obya.blueprint.customer.web.CustomerController;
import edu.obya.blueprint.customer.web.CustomerWebConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.function.Supplier;

@ActiveProfiles({"test","jpa"})
@AutoConfigureEmbeddedDatabase
@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
public class CustomerCucumberBootstrap {

    @Import(value = {
            CustomerController.class,
            CustomerWebConfiguration.class,
            WebSecurityConfiguration.class,
            WebValidationConfiguration.class,
            ExceptionHandling.class,
            CustomerApplicationConfiguration.class,
            CustomerJpaConfiguration.class,
    })
    @TestConfiguration
    static class TestConfig {

        @MockBean
        public Supplier<CustomerId> customerIdSupplier;
    }
}
