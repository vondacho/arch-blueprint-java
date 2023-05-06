package edu.obya.blueprint.customer.application;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static edu.obya.blueprint.customer.domain.model.TestCustomer.TEST_CUSTOMER_STATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestCustomerIn {
    public static final CustomerDto TEST_CUSTOMER_IN = CustomerDto.builder()
            .firstName(TEST_CUSTOMER_STATE.getFirstName())
            .lastName(TEST_CUSTOMER_STATE.getLastName())
            .build();
}
