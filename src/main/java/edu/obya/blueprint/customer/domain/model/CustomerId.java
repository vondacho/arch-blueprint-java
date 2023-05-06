package edu.obya.blueprint.customer.domain.model;

import lombok.*;

import java.util.UUID;

@Builder
@Value
public class CustomerId {
    UUID id;

    public static CustomerId parse(String id) {
        return CustomerId.builder().id(UUID.fromString(id)).build();
    }

    public String toString() {
        return id.toString();
    }
}
