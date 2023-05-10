package edu.obya.blueprint.customer.application;

import com.structurizr.annotation.Component;
import com.structurizr.annotation.UsesComponent;
import edu.obya.blueprint.common.util.search.FindCriteria;
import edu.obya.blueprint.customer.domain.model.Customer;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.domain.model.CustomerState;
import edu.obya.blueprint.customer.domain.service.CustomerAlreadyExistException;
import edu.obya.blueprint.customer.domain.service.CustomerNotFoundException;
import edu.obya.blueprint.customer.domain.service.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Component(description = "Manages customer entities", technology = "Java")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequiredArgsConstructor
public class CustomerService {

    @UsesComponent(description = "stores customer entities using", technology = "Java")
    private final CustomerRepository repository;
    private final Supplier<CustomerId> idSupplier;

    @Transactional(readOnly = true)
    public Customer get(@NonNull CustomerId customerId) {
        return repository
                .findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Transactional(readOnly = true)
    public List<Customer> list(@NonNull List<FindCriteria> criteria) {
        return repository
                .findByCriteria(criteria)
                .stream()
                .toList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerId create(@NonNull CustomerDto customerDto) {
        if (repository.findByFirstNameAndLastName(customerDto.firstName, customerDto.lastName).isPresent()) {
            throw new CustomerAlreadyExistException(customerDto.firstName, customerDto.lastName);
        }
        val customerId = idSupplier.get();
        repository.add(customerId, map(customerDto));
        return customerId;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(@NonNull CustomerDto customerDto, @NonNull CustomerId customerId) {
        repository.findByFirstNameAndLastName(customerDto.firstName, customerDto.lastName).ifPresent(existing -> {
            if (!existing.getId().equals(customerId)) {
                throw new CustomerAlreadyExistException(customerDto.firstName, customerDto.lastName);
            }
        });
        repository.update(customerId, map(customerDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(@NonNull CustomerId customerId) {
        repository.remove(customerId);
    }

    private CustomerState map(CustomerDto dto) {
        return CustomerState.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }
}
