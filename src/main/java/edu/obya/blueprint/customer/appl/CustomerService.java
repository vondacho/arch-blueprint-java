package edu.obya.blueprint.customer.appl;

import edu.obya.blueprint.common.util.search.FindCriteria;
import edu.obya.blueprint.customer.domain.*;
import edu.obya.blueprint.customer.web.CustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final Supplier<CustomerId> idSupplier = () -> CustomerId.of(UUID.randomUUID());

    @Transactional(readOnly = true)
    public CustomerDto get(CustomerId customerId) {
        return repository
                .findById(customerId)
                .map(CustomerDto::from)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> findByCriteria(List<FindCriteria> criteria) {
        return repository
                .findByCriteria(criteria)
                .stream()
                .map(CustomerDto::from)
                .toList();
    }

    public CustomerId create(CustomerDto customerDto) {
        val customerId = idSupplier.get();
        repository.add(map(customerDto, customerId));
        return customerId;
    }

    public CustomerId create(CustomerDto customerDto, CustomerId customerId) {
        if (repository.findById(customerId).isPresent()) {
            throw new CustomerAlreadyExistException(customerId);
        }
        repository.add(map(customerDto, customerId));
        return customerId;
    }

    public void update(CustomerDto customerDto, CustomerId customerId) {
        if (!repository.update(map(customerDto, customerId))) {
            throw new CustomerNotFoundException(customerId);
        }
    }

    public void remove(CustomerId customerId) {
        if (!repository.remove(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
    }

    private Customer map(CustomerDto dto, CustomerId id) {
        return Customer.builder()
                .id(id)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }
}
