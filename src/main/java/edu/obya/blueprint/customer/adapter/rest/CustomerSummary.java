package edu.obya.blueprint.customer.adapter.rest;

import edu.obya.blueprint.customer.domain.model.Customer;
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
                .build();
    }

    public String getFullName() {
        return fullName == null ? String.format("%s %s", firstName, lastName) : fullName;
    }
}
