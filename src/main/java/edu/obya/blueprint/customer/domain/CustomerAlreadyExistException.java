package edu.obya.blueprint.customer.domain;

public class CustomerAlreadyExistException extends RuntimeException {

    public CustomerAlreadyExistException(CustomerId customerId) {
        super(String.format("customer %s already exists.", customerId.getId()));
    }
}
