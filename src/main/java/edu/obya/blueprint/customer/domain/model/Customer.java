package edu.obya.blueprint.customer.domain.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Customer {
    @NonNull
    CustomerId id;
    @NonNull
    CustomerState state;
}
