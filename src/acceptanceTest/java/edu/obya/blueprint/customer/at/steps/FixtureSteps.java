package edu.obya.blueprint.customer.at.steps;

import edu.obya.blueprint.customer.domain.model.Customer;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static edu.obya.blueprint.customer.at.TestUser.*;
import static edu.obya.blueprint.customer.at.infra.TestContext.set;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class FixtureSteps {

    private final Supplier<CustomerId> customerIdSupplier;
    private final JdbcTemplate jdbcTemplate;

    @Given("a following set of existing customers")
    public void aFollowingSetOfExistingCustomers(List<Customer> customers) {
        set("existingCustomers", customers);

        customers.forEach(customer ->
            jdbcTemplate.update("insert into customer values (?, ?, ?, ?)",
                    UUID.randomUUID(), customer.getId().getId(), customer.getState().getFirstName(), customer.getState().getLastName()));
    }

    @Given("the next identifier is {customerId}")
    public void theNextIdentifierIs(CustomerId id) {
        when(customerIdSupplier.get()).thenReturn(id);
    }

    @Given("user has read permission")
    public void aUserHasReadPermission() {
        set("userId", TEST_USER_NAME);
        set("userPassword", TEST_USER_PASSWORD);
    }

    @Given("user has write permission")
    public void aUserHasWritePermission() {
        set("userId", TEST_USER_NAME);
        set("userPassword", TEST_USER_PASSWORD);
    }

    @Given("user has remove permission")
    public void aUserHasRemovePermission() {
        set("userId", TEST_ADMIN_NAME);
        set("userPassword", TEST_ADMIN_PASSWORD);
    }

    @Given("user has no permission")
    public void aUserHasNoPermission() {
        set("userId", TEST_ANONYMOUS_NAME);
        set("userPassword", TEST_ANONYMOUS_PASSWORD);
    }

    @Given("user is not authenticated")
    public void aUserIsNotAuthenticated() {
        set("userId", "");
        set("userPassword", "");
    }
}
