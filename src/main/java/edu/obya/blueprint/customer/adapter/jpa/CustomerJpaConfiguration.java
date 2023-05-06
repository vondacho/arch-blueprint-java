package edu.obya.blueprint.customer.adapter.jpa;

import edu.obya.blueprint.customer.domain.service.CustomerRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Profile("jpa")
@EntityScan(basePackageClasses = JpaCustomer.class)
@EnableJpaRepositories(basePackageClasses = JpaCustomerRepository.class)
@Configuration
public class CustomerJpaConfiguration {

    @Bean
    public CustomerRepository customerRepository(JpaCustomerRepository repository) {
        return new CustomerRepositoryJpaAdapter(repository);
    }
}
