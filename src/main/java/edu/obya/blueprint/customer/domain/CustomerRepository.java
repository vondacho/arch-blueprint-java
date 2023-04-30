package edu.obya.blueprint.customer.domain;

import edu.obya.blueprint.common.util.search.FindCriteria;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById(@NonNull CustomerId customerId);

    Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    List<Customer> findByCriteria(List<FindCriteria> criteria);

    void add(@NonNull CustomerId id, @NonNull CustomerState state);

    void update(@NonNull CustomerId id, @NonNull CustomerState state);

    void remove(@NonNull CustomerId id);
}
