package edu.obya.blueprint.customer.domain.service;

import edu.obya.blueprint.customer.domain.model.CustomerId;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(CustomerId customerId) {
        super(String.format("customer %s does not exist.", customerId.getId()));
    }
}
