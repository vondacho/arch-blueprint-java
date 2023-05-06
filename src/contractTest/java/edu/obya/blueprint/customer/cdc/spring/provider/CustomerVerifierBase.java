package edu.obya.blueprint.customer.cdc.spring.provider;

import edu.obya.blueprint.customer.domain.model.TestCustomer;
import edu.obya.blueprint.customer.cdc.TestUserTokens;
import edu.obya.blueprint.customer.domain.service.CustomerRepository;
import edu.obya.blueprint.customer.adapter.rest.CustomerController;
import io.micrometer.core.instrument.MeterRegistry;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("test")
@AutoConfigureMessageVerifier
@Import(CustomerVerifierContextConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class CustomerVerifierBase {

    @Autowired
    CustomerController customerController;
    @Autowired
    FilterChainProxy springSecurityFilterChain;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    MeterRegistry registry;

    @BeforeEach
    void setup() {
        Mockito.when(customerRepository.findById(any())).thenReturn(Optional.of(TestCustomer.TEST_CUSTOMER));

        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders
                .standaloneSetup(customerController)
                .alwaysDo(print())
                .apply(springSecurity(springSecurityFilterChain));
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

    String uriWithCustomerId() {
        return String.format("/customers/%s", TestCustomer.TEST_CUSTOMER_ID.getId());
    }

    String userAuthToken() {
        return TestUserTokens.TEST_USER_TOKEN;
    }

    String adminAuthToken() {
        return TestUserTokens.TEST_ADMIN_TOKEN;
    }
}
