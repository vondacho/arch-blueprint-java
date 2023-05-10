package edu.obya.blueprint.customer.adapter.jpa;

import com.structurizr.annotation.Component;
import com.structurizr.annotation.UsesComponent;
import edu.obya.blueprint.common.adapter.jpa.SpecificationBuilder;
import edu.obya.blueprint.common.util.search.FindCriteria;
import edu.obya.blueprint.customer.domain.model.Customer;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.domain.model.CustomerState;
import edu.obya.blueprint.customer.domain.service.CustomerNotFoundException;
import edu.obya.blueprint.customer.domain.service.CustomerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Component(description = "Adapter for customer entity storage using JPA", technology = "Java")
@RequiredArgsConstructor
public class CustomerRepositoryJpaAdapter implements CustomerRepository {

    @UsesComponent(description = "stores customer data using", technology = "Java")
    private final JpaCustomerRepository repository;
    private final Supplier<UUID> pkSupplier = UUID::randomUUID;

    @Override
    public Optional<Customer> findById(@NonNull CustomerId customerId) {
        return repository.findByLogicalId(customerId).stream().findFirst().map(this::toCustomer);
    }

    @Override
    public Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName).stream().findFirst().map(this::toCustomer);
    }

    @Override
    public List<Customer> findByCriteria(List<FindCriteria> criteria) {
        SpecificationBuilder<JpaCustomer> builder = new SpecificationBuilder<>();
        criteria.forEach(builder::with);
        return repository.findAll(builder.build().orElse(null))
                .stream()
                .map(this::toCustomer)
                .toList();
    }

    @Override
    public void add(@NonNull CustomerId id, @NonNull CustomerState state) {
        repository.save(toData(state, id));
    }

    @Override
    public void update(@NonNull CustomerId customerId, @NonNull CustomerState customer) {
        JpaCustomer data = repository.findByLogicalId(customerId).stream().findFirst()
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        data.set(customer, customerId);
        repository.save(data);
    }

    @Override
    public void remove(@NonNull CustomerId customerId) {
        JpaCustomer data = repository.findByLogicalId(customerId).stream().findFirst()
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        repository.deleteById(data.getPk());
    }

    private JpaCustomer toData(CustomerState state, CustomerId id) {
        return JpaCustomer.from(state, id, pkSupplier.get());
    }

    private Customer toCustomer(JpaCustomer data) {
        return Customer.builder()
                .id(data.getLogicalId())
                .state(CustomerState.builder()
                    .firstName(data.getFirstName())
                    .lastName(data.getLastName())
                    .build())
                .build();
    }
}
