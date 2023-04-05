package edu.obya.blueprint.customer.web;

import edu.obya.blueprint.customer.domain.Customer;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CustomerSummary {
    String id;
    String fullName;
    String firstName;
    String lastName;

    public static CustomerSummary from(Customer customer) {
        return CustomerSummary.builder()
                .id(customer.getId().getId().toString())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fullName(String.format("%s %s", customer.getFirstName(), customer.getLastName()))
                .build();
    }
}
