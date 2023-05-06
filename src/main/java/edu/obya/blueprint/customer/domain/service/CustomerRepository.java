package edu.obya.blueprint.customer.domain.service;

import com.structurizr.annotation.Component;
import edu.obya.blueprint.common.util.search.FindCriteria;
import edu.obya.blueprint.customer.domain.model.Customer;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.domain.model.CustomerState;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@Component(description = "Repository interface for customer entity storage", technology = "Java")
public interface CustomerRepository {

    Optional<Customer> findById(@NonNull CustomerId customerId);

    Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    List<Customer> findByCriteria(List<FindCriteria> criteria);

    void add(@NonNull CustomerId id, @NonNull CustomerState state);

    void update(@NonNull CustomerId id, @NonNull CustomerState state);

    void remove(@NonNull CustomerId id);
}
