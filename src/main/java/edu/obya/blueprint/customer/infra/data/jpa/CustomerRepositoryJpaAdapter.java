package edu.obya.blueprint.customer.infra.data.jpa;

import edu.obya.blueprint.common.infra.data.jpa.SpecificationBuilder;
import edu.obya.blueprint.common.util.search.FindCriteria;
import edu.obya.blueprint.customer.domain.Customer;
import edu.obya.blueprint.customer.domain.CustomerId;
import edu.obya.blueprint.customer.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class CustomerRepositoryJpaAdapter implements CustomerRepository {
    private final JpaCustomerRepository repository;
    private final Supplier<UUID> pkSupplier = UUID::randomUUID;

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        return repository.findByLogicalId(customerId).map(this::toCustomer);
    }

    @Override
    public List<Customer> findByCriteria(List<FindCriteria> criteria) {
        SpecificationBuilder<JpaCustomer> builder = SpecificationBuilder.from(criteria);
        return repository
                .findAll(builder.build())
                .stream()
                .map(this::toCustomer)
                .toList();
    }

    @Override
    public void add(Customer customer) {
        repository.save(toData(customer));
    }

    @Override
    public boolean update(Customer customer) {
        return repository.findByLogicalId(customer.getId())
                .map(data -> data.set(customer))
                .map(repository::save)
                .isPresent();
    }

    @Override
    public boolean remove(CustomerId customerId) {
        return repository.findByLogicalId(customerId)
                .map(data -> {
                    repository.deleteById(data.getPk());
                    return data;
                })
                .isPresent();
    }

    private JpaCustomer toData(Customer entity) {
        return JpaCustomer.from(entity, pkSupplier.get());
    }

    private Customer toCustomer(JpaCustomer data) {
        return Customer.builder()
                .id(data.getLogicalId())
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .build();
    }
}
