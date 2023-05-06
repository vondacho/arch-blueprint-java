package edu.obya.blueprint.customer.application;

import edu.obya.blueprint.customer.domain.model.CustomerId;
import edu.obya.blueprint.customer.domain.service.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.UUID;
import java.util.function.Supplier;

@EnableTransactionManagement
@Configuration
public class CustomerApplicationConfiguration {

    @Bean
    public CustomerService customerService(CustomerRepository repository, Supplier<CustomerId> idSupplier) {
        return new CustomerService(repository, idSupplier);
    }

    @Bean
    public Supplier<CustomerId> customerIdSupplier() {
        return () -> CustomerId.builder().id(UUID.randomUUID()).build();
    }
}
