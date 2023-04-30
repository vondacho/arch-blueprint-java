package edu.obya.blueprint.customer.application;

import edu.obya.blueprint.customer.domain.CustomerState;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
public class CustomerDto {
    String firstName;
    String lastName;

    public static CustomerDto from(CustomerState customer) {
        return CustomerDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }
}
