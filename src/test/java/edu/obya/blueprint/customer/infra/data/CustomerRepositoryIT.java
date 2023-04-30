package edu.obya.blueprint.customer.infra.data;

import edu.obya.blueprint.customer.domain.Customer;
import edu.obya.blueprint.customer.domain.CustomerId;
import edu.obya.blueprint.customer.domain.CustomerRepository;
import edu.obya.blueprint.customer.domain.CustomerState;
import edu.obya.blueprint.customer.infra.data.jpa.CustomerJpaConfiguration;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles({"jpa", "test"})
@Import(CustomerJpaConfiguration.class)
@AutoConfigureEmbeddedDatabase
@DataJpaTest
public class CustomerRepositoryIT {

    @Autowired
    CustomerRepository repository;
    @Autowired
    PlatformTransactionManager transactionManager;

    @Test
    @Transactional
    public void shouldCreateAndFindAndModifyAndRemoveACustomer() {
        CustomerId id = CustomerId.builder().id(UUID.randomUUID()).build();
        CustomerState state = CustomerState.builder()
                        .firstName("test")
                        .lastName("test")
                        .build();
        repository.add(id, state);

        Optional<Customer> retrieved = repository.findById(id);
        assertTrue(retrieved.isPresent());
        assertEquals(state, retrieved.get().getState());

        CustomerState modified = CustomerState.builder()
                .firstName("john")
                .lastName("doe")
                .build();
        repository.update(id, modified);

        retrieved = repository.findById(id);
        assertTrue(retrieved.isPresent());
        assertEquals(modified, retrieved.get().getState());

        repository.remove(id);
        assertTrue(repository.findById(id).isEmpty());
    }

}
