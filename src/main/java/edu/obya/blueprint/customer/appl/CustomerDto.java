package edu.obya.blueprint.customer.appl;

import edu.obya.blueprint.customer.domain.Customer;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDto {
    String id;
    String firstName;
    String lastName;

    public static CustomerDto from(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId().getId().toString())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }
}
