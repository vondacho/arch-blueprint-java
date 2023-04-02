package edu.obya.blueprint.customer.appl;

import edu.obya.blueprint.customer.domain.CustomerRepository;
import edu.obya.blueprint.customer.infra.data.jpa.CustomerRepositoryJpaAdapter;
import edu.obya.blueprint.customer.infra.data.jpa.JpaCustomer;
import edu.obya.blueprint.customer.infra.data.jpa.JpaCustomerRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
public class CustomerApplicationConfiguration {

    @Bean
    public CustomerService customerService(CustomerRepository repository) {
        return new CustomerService(repository);
    }
}
