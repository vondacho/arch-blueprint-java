package edu.obya.blueprint.customer.at;

import edu.obya.blueprint.config.ExceptionHandling;
import edu.obya.blueprint.config.WebSecurityConfiguration;
import edu.obya.blueprint.config.WebValidationConfiguration;
import edu.obya.blueprint.customer.application.CustomerApplicationConfiguration;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.adapter.jpa.CustomerJpaConfiguration;
import edu.obya.blueprint.customer.adapter.rest.CustomerController;
import edu.obya.blueprint.customer.adapter.rest.CustomerWebConfiguration;
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
