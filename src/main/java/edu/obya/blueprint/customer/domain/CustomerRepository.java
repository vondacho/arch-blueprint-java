package edu.obya.blueprint.customer.domain;

import edu.obya.blueprint.common.util.search.FindCriteria;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById(CustomerId customerId);

    List<Customer> findByCriteria(List<FindCriteria> criteria);

    void add(Customer customer);

    boolean update(Customer customer);

    boolean remove(CustomerId customerId);
}
