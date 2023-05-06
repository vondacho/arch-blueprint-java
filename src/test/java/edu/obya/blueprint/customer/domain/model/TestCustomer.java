package edu.obya.blueprint.customer.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestCustomer {
    public static final CustomerId TEST_CUSTOMER_ID = CustomerId.parse("64a0f7d1-7b25-412d-b1e0-abacde3c21cd");

    public static final CustomerState TEST_CUSTOMER_STATE = CustomerState.builder()
            .firstName("John")
            .lastName("Doe")
            .build();

    public static final Customer TEST_CUSTOMER = Customer.builder()
            .id(TEST_CUSTOMER_ID)
            .state(TEST_CUSTOMER_STATE)
            .build();
}
