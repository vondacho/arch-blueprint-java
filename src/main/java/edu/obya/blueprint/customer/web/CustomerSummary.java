package edu.obya.blueprint.customer.web;

import edu.obya.blueprint.customer.domain.Customer;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
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
                .firstName(customer.getState().getFirstName())
                .lastName(customer.getState().getLastName())
                .fullName(String.format("%s %s", customer.getState().getFirstName(), customer.getState().getLastName()))
                .build();
    }
}
