package edu.obya.blueprint.customer.domain;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(CustomerId customerId) {
        super(String.format("customer %s does not exist.", customerId.getId()));
    }
}
