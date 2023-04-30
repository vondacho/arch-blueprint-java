package edu.obya.blueprint.customer.at.steps;

import edu.obya.blueprint.customer.at.infra.TestContext;
import edu.obya.blueprint.customer.domain.Customer;
import edu.obya.blueprint.customer.domain.CustomerId;
import edu.obya.blueprint.customer.domain.CustomerState;
import edu.obya.blueprint.customer.web.CustomerSummary;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
public class AssertionSteps {

    private final JdbcTemplate jdbcTemplate;

    @Then("the set of existing customers is the following")
    public void theSetOfExistingCustomersIs(List<Customer> customers) {
        val existingCustomers = jdbcTemplate.query("select id, firstname, lastname from customer", (rs, rowNum) ->
                Customer.builder()
                        .id(CustomerId.parse(rs.getString("id")))
                        .state(CustomerState.builder()
                                .firstName(rs.getString("firstname"))
                                .lastName(rs.getString("lastname"))
                                .build())
                        .build());

        assertThat(existingCustomers).containsExactlyInAnyOrderElementsOf(customers);
    }

    @Then("the set of existing customer ids is the following")
    public void theSetOfExistingCustomerIdsIs(List<CustomerId> customerIds) {
        val existingCustomerIds = jdbcTemplate.query("select id from customer", (rs, rowNum) ->
                CustomerId.parse(rs.getString("id")));

        assertThat(existingCustomerIds).containsExactlyInAnyOrderElementsOf(customerIds);
    }

    @Then("the set of existing customers is left unchanged")
    public void theSetOfExistingCustomersIsLeftUnchanged() {
        val customers = jdbcTemplate.query("select id, firstname, lastname from customer", (rs, rowNum) ->
                Customer.builder()
                        .id(CustomerId.parse(rs.getString("id")))
                        .state(CustomerState.builder()
                                .firstName(rs.getString("firstname"))
                                .lastName(rs.getString("lastname"))
                                .build())
                        .build());

        assertThat(customers).containsExactlyInAnyOrderElementsOf(TestContext.at("existingCustomers"));
    }

    @Then("the returned customer id is {customerId}")
    public void theReturnedIdIs(CustomerId id) throws Exception {
        TestContext.atAs("result", ResultActions.class).andExpect(content().string(id.getId().toString()));
    }

    @Then("the response status is {status}")
    public void theResponseStatusIs(HttpStatus expected) throws Exception {
        TestContext.atAs("result", ResultActions.class).andExpect(status().is(expected.value()));
    }

    @Then("the response contains the following customers")
    public void theResponseContainsTheFollowingCustomers(List<CustomerSummary> expected) throws Exception {
        val result = TestContext.atAs("result", ResultActions.class);
        for (CustomerSummary expectedOne: expected) {
            result.andExpect(
                    jsonPath("$[?(@.firstName == '%s' && @.lastName == '%s' && @.fullName == '%s' && @.id == '%s')]",
                            expectedOne.getFirstName(),
                            expectedOne.getLastName(),
                            expectedOne.getFullName(),
                            expectedOne.getId()
                    ).exists());
        }
    }

    @Then("the response contains only the following customer")
    public void theResponseContainsOnlyTheFollowingCustomer(List<CustomerSummary> expected) throws Exception {
        val expectedOne = expected.get(0);
        TestContext.atAs("result", ResultActions.class)
                .andExpect(jsonPath("$.firstName").value(expectedOne.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedOne.getLastName()))
                .andExpect(jsonPath("$.fullName").value(expectedOne.getFullName()))
                .andExpect(jsonPath("$.id").value(expectedOne.getId()));
    }
}
