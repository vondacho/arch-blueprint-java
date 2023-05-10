package edu.obya.blueprint.customer.cdc.pact.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.adapter.rest.CustomerSummary;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static edu.obya.blueprint.customer.adapter.rest.TestCustomerOut.TEST_CUSTOMER_OUT;
import static edu.obya.blueprint.customer.application.TestCustomerIn.TEST_CUSTOMER_IN;
import static edu.obya.blueprint.customer.domain.model.TestCustomer.*;
import static edu.obya.blueprint.customer.cdc.TestWebUserTokens.TEST_ADMIN_TOKEN;
import static edu.obya.blueprint.customer.cdc.TestWebUserTokens.TEST_USER_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@ExtendWith(PactConsumerTestExt.class)
@PactDirectory("src/contractTest/resources/customer/cdc/pact")
public class CustomerPactConsumerATest {

    static final String BASIC_AUTH_REGEX = "Basic (?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?";
    static final String URI_WITH_ID_REGEX = "^/customers/[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$";

    @Pact(consumer = "consumerA", provider = "customerAPI")
    public V4Pact getCustomer(PactBuilder builder) {
        return builder
                .usingLegacyDsl()
                .given("an existing customer")
                .uponReceiving("get existing customer interaction")
                .method("GET")
                .matchPath(URI_WITH_ID_REGEX, String.format("/customers/%s", TEST_CUSTOMER_ID))
                .matchHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE, APPLICATION_JSON_VALUE)
                .matchHeader(AUTHORIZATION, BASIC_AUTH_REGEX, TEST_USER_TOKEN)
                .willRespondWith()
                .status(200)
                .body(newJsonBody(object -> {
                    object.uuid("id", TEST_CUSTOMER_ID.getId());
                    object.stringType("firstName", TEST_CUSTOMER_OUT.getFirstName());
                    object.stringType("lastName", TEST_CUSTOMER_OUT.getLastName());
                    object.stringType("fullName", TEST_CUSTOMER_OUT.getFullName());
                }).build())
                .toPact()
                .asV4Pact().get();
    }

    @Pact(consumer = "consumerA", provider = "customerAPI")
    public V4Pact addCustomer(PactBuilder builder) {
        return builder
                .usingLegacyDsl()
                .given("with default customer id")
                .uponReceiving("add new customer interaction")
                .method("POST")
                .path("/customers")
                .matchHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE, APPLICATION_JSON_VALUE)
                .matchHeader(AUTHORIZATION, BASIC_AUTH_REGEX, TEST_USER_TOKEN)
                .body(newJsonBody(object -> {
                    object.stringType("firstName", TEST_CUSTOMER_STATE.getFirstName());
                    object.stringType("lastName", TEST_CUSTOMER_STATE.getLastName());
                }).build())
                .willRespondWith()
                .status(201)
                .body(TEST_CUSTOMER_ID.getId().toString(), TEXT_PLAIN_VALUE)
                .toPact()
                .asV4Pact().get();
    }

    @Pact(consumer = "consumerA", provider = "customerAPI")
    public V4Pact replaceCustomer(PactBuilder builder) {
        return builder
                .usingLegacyDsl()
                .given("an existing customer")
                .uponReceiving("replace existing customer interaction")
                .method("PUT")
                .matchPath(URI_WITH_ID_REGEX, String.format("/customers/%s", TEST_CUSTOMER_ID))
                .matchHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE, APPLICATION_JSON_VALUE)
                .matchHeader(AUTHORIZATION, BASIC_AUTH_REGEX, TEST_USER_TOKEN)
                .body(newJsonBody(object -> {
                    object.stringType("firstName", TEST_CUSTOMER_STATE.getFirstName());
                    object.stringType("lastName", TEST_CUSTOMER_STATE.getLastName());
                }).build())
                .willRespondWith()
                .status(204)
                .toPact()
                .asV4Pact().get();
    }

    @Pact(consumer = "consumerA", provider = "customerAPI")
    public V4Pact removeCustomer(PactBuilder builder) {
        return builder
                .usingLegacyDsl()
                .given("an existing customer")
                .uponReceiving("remove existing customer interaction")
                .method("DELETE")
                .matchPath(URI_WITH_ID_REGEX, String.format("/customers/%s", TEST_CUSTOMER_ID))
                .matchHeader(AUTHORIZATION, BASIC_AUTH_REGEX, TEST_ADMIN_TOKEN)
                .willRespondWith()
                .status(204)
                .toPact()
                .asV4Pact().get();
    }

    @Test
    @PactTestFor(pactMethod = "getCustomer")
    void getCustomer_whenExists(MockServer mockServer) {
        RestTemplate restTemplate = templateWithAuth(mockServer);
        CustomerSummary result = fetchCustomer(restTemplate);
        Assertions.assertThat(result).isEqualTo(TEST_CUSTOMER_OUT);
    }

    private CustomerSummary fetchCustomer(RestTemplate restTemplate) {
        return restTemplate.getForObject("/customers/{id}", CustomerSummary.class, TEST_CUSTOMER_ID);
    }

    @Test
    @PactTestFor(pactMethod = "addCustomer")
    void addCustomer(MockServer mockServer) {
        CustomerId result = CustomerId.parse(templateWithAuth(mockServer).postForObject("/customers", TEST_CUSTOMER_IN, String.class));
        Assertions.assertThat(result).isEqualTo(TEST_CUSTOMER_ID);
    }

    @Test
    @PactTestFor(pactMethod = "replaceCustomer")
    void replaceCustomer(MockServer mockServer) {
        templateWithAuth(mockServer).put("/customers/{id}", TEST_CUSTOMER_IN, TEST_CUSTOMER_ID);
    }

    @Test
    @PactTestFor(pactMethod = "removeCustomer")
    void removeCustomer(MockServer mockServer) {
        templateWithAuthElevatedMode(mockServer).delete("/customers/{id}", TEST_CUSTOMER_ID);
    }

    private RestTemplate templateWithAuth(MockServer mockServer) {
        return new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .defaultHeader(AUTHORIZATION, TEST_USER_TOKEN)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    private RestTemplate templateWithAuthElevatedMode(MockServer mockServer) {
        return new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .defaultHeader(AUTHORIZATION, TEST_ADMIN_TOKEN)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }
}
