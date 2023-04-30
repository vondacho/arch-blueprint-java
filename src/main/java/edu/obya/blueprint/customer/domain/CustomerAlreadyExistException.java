package edu.obya.blueprint.customer.domain;

public class CustomerAlreadyExistException extends RuntimeException {

    public CustomerAlreadyExistException(String firstName, String lastName) {
        super(String.format("customer %s %s already exists.", firstName, lastName));
    }
}
