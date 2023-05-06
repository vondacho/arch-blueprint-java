package edu.obya.blueprint.customer.cdc.spring.provider;

import edu.obya.blueprint.config.WebSecurityConfiguration;
import edu.obya.blueprint.config.WebValidationConfiguration;
import edu.obya.blueprint.customer.application.CustomerApplicationConfiguration;
import edu.obya.blueprint.customer.adapter.rest.CustomerController;
import org.springframework.boot.actuate.autoconfigure.context.ShutdownEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.info.InfoEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration(exclude = {
        LiquibaseAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        MetricsAutoConfiguration.class,
        HealthEndpointAutoConfiguration.class,
        InfoEndpointAutoConfiguration.class,
        ShutdownEndpointAutoConfiguration.class
})
@Import({
        CustomerController.class,
        WebSecurityConfiguration.class,
        WebValidationConfiguration.class,
        CustomerApplicationConfiguration.class
})
@TestConfiguration
public class CustomerVerifierContextConfiguration {
}
