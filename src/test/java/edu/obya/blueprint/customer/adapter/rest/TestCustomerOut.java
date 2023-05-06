package edu.obya.blueprint.customer.adapter.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static edu.obya.blueprint.customer.domain.model.TestCustomer.TEST_CUSTOMER_ID;
import static edu.obya.blueprint.customer.domain.model.TestCustomer.TEST_CUSTOMER_STATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestCustomerOut {
    public static final CustomerSummary TEST_CUSTOMER_OUT = CustomerSummary.builder()
            .id(TEST_CUSTOMER_ID.getId().toString())
            .firstName(TEST_CUSTOMER_STATE.getFirstName())
            .lastName(TEST_CUSTOMER_STATE.getLastName())
            .build();
}
