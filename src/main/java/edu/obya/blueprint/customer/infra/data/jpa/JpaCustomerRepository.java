package edu.obya.blueprint.customer.infra.data.jpa;

import com.structurizr.annotation.Component;
import com.structurizr.annotation.UsesContainer;
import edu.obya.blueprint.customer.domain.CustomerId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

@Component(description = "Spring-Data repository for customer data storage", technology = "Java/JPA/Hibernate")
@UsesContainer(name = "database", description = "reads/writes from/to", technology = "SQL")
public interface JpaCustomerRepository
        extends PagingAndSortingRepository<JpaCustomer, UUID>, JpaSpecificationExecutor<JpaCustomer> {

    Optional<JpaCustomer> findByLogicalId(CustomerId customerId);
    Optional<JpaCustomer> findByFirstNameAndLastName(String firstName, String lastName);
}
