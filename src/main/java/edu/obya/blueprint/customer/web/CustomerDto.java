package edu.obya.blueprint.customer.web;

import edu.obya.blueprint.customer.domain.Customer;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDto {
    String id;
    String fullName;
    String firstName;
    String lastName;

    public static CustomerDto from(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId().getId().toString())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fullName(String.format("%s %s", customer.getFirstName(), customer.getLastName()))
                .build();
    }
}
