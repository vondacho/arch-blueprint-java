package edu.obya.blueprint.customer.web;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomerHealthCheck implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up().withDetail("heartRate", "50").build();
    }
}
