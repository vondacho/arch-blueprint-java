package edu.obya.blueprint.customer;

import edu.obya.blueprint.customer.application.CustomerDto;
import edu.obya.blueprint.customer.domain.Customer;
import edu.obya.blueprint.customer.domain.CustomerState;
import edu.obya.blueprint.customer.domain.CustomerId;
import edu.obya.blueprint.customer.web.CustomerSummary;
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

    public static final CustomerDto TEST_CUSTOMER_IN = CustomerDto.builder()
            .firstName("John")
            .lastName("Doe")
            .build();

    public static final CustomerSummary TEST_CUSTOMER_OUT = CustomerSummary.builder()
            .id(TEST_CUSTOMER_ID.getId().toString())
            .firstName(TEST_CUSTOMER_STATE.getFirstName())
            .lastName(TEST_CUSTOMER_STATE.getLastName())
            .fullName(String.format("%s %s", TEST_CUSTOMER_STATE.getFirstName(), TEST_CUSTOMER_STATE.getLastName()))
            .build();
}
