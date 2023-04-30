package edu.obya.blueprint.customer.application;

import edu.obya.blueprint.customer.domain.Customer;
import edu.obya.blueprint.customer.domain.CustomerNotFoundException;
import edu.obya.blueprint.customer.domain.CustomerId;
import edu.obya.blueprint.customer.infra.data.jpa.CustomerJpaConfiguration;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
        CustomerDto dto = CustomerDto.builder()
                        .firstName("test")
                        .lastName("test")
                        .build();
        CustomerId id = service.create(dto);

        Customer retrieved = service.get(id);
        assertThat(retrieved.getState())
                .extracting("firstName", "lastName")
                .containsExactly(dto.firstName, dto.lastName);

        CustomerDto modified = CustomerDto.builder()
                .firstName("john")
                .lastName("doe")
                .build();
        service.update(modified, id);

        retrieved = service.get(id);
        assertThat(retrieved.getState())
                .extracting("firstName", "lastName")
                .containsExactly(modified.firstName, modified.lastName);

        service.remove(id);
        assertThatThrownBy(() -> { service.get(id); }).isInstanceOf(CustomerNotFoundException.class);
    }

}
