package edu.obya.blueprint.customer.cdc.pact.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.domain.service.CustomerRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.function.Supplier;

import static edu.obya.blueprint.customer.domain.model.TestCustomer.TEST_CUSTOMER;
import static edu.obya.blueprint.customer.domain.model.TestCustomer.TEST_CUSTOMER_ID;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@Provider("customerAPI")
@PactFolder("customer/cdc/pact")
@Import(CustomerPactContextConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerPactProviderTest {

    @LocalServerPort
    int port;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    Supplier<CustomerId> idSupplier;
    @MockBean
    MeterRegistry registry;

    @BeforeEach
    void setUp(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("an existing customer")
    void anExistingCustomerState() {
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.of(TEST_CUSTOMER));
    }

    @State("with default customer id")
    void withDefaultCustomerIdState() {
        Mockito.when(idSupplier.get()).thenReturn(TEST_CUSTOMER_ID);
    }
}
