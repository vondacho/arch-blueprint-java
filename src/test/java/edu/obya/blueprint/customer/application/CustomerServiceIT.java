package edu.obya.blueprint.customer.application;

import edu.obya.blueprint.customer.adapter.jpa.CustomerJpaConfiguration;
import edu.obya.blueprint.customer.domain.model.Customer;
import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.domain.service.CustomerNotFoundException;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static edu.obya.blueprint.customer.application.TestCustomerIn.TEST_CUSTOMER_IN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ActiveProfiles({"jpa", "test"})
@Import({CustomerJpaConfiguration.class, CustomerApplicationConfiguration.class})
@AutoConfigureEmbeddedDatabase
@DataJpaTest
public class CustomerServiceIT {

    @Autowired
    CustomerService service;

    @Test
    @Transactional
    public void shouldCreateAndFindAndModifyAndRemoveACustomer() {
        CustomerId id = service.create(TEST_CUSTOMER_IN);

        Customer retrieved = service.get(id);
        assertThat(retrieved.getState())
                .extracting("firstName", "lastName")
                .containsExactly(TEST_CUSTOMER_IN.getFirstName(), TEST_CUSTOMER_IN.getLastName());

        CustomerDto modified = CustomerDto.builder()
                .firstName("Bob")
                .lastName("Dylan")
                .build();
        service.update(modified, id);

        retrieved = service.get(id);
        assertThat(retrieved.getState())
                .extracting("firstName", "lastName")
                .containsExactly(modified.getFirstName(), modified.getLastName());

        service.remove(id);
        assertThatThrownBy(() -> service.get(id)).isInstanceOf(CustomerNotFoundException.class);
    }

}
