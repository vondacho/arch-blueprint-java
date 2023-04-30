package edu.obya.blueprint.customer.infra.data.jpa;

import edu.obya.blueprint.customer.domain.CustomerId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaCustomerRepository
        extends PagingAndSortingRepository<JpaCustomer, UUID>, JpaSpecificationExecutor<JpaCustomer> {

    Optional<JpaCustomer> findByLogicalId(CustomerId customerId);
    Optional<JpaCustomer> findByFirstNameAndLastName(String firstName, String lastName);
}
