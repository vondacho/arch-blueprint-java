package edu.obya.blueprint.customer.domain;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class CustomerId {
    UUID id;

    public static CustomerId from(String id) {
        return CustomerId.of(UUID.fromString(id));
    }
}
