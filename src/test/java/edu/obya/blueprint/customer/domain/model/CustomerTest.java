package edu.obya.blueprint.customer.domain.model;

import edu.obya.blueprint.customer.domain.model.Customer;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.domain.model.CustomerState;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CustomerTest {
    @Test
    void should_create_customer_as_expected() {
        val customer = Customer.builder()
                .id(CustomerId.parse("4cc53bf9-6118-4f94-99e9-8ff8b5a4c044"))
                .state(CustomerState.builder()
                        .firstName("test")
                        .lastName("test")
                        .build())
                .build();

        assertThat(customer)
                .extracting("state.firstName", "state.lastName")
                .containsExactly("test", "test");
    }
}
