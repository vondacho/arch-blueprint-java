package edu.obya.blueprint.customer.at.config;

import edu.obya.blueprint.customer.application.CustomerDto;
import edu.obya.blueprint.customer.domain.Customer;
import edu.obya.blueprint.customer.domain.CustomerId;
import edu.obya.blueprint.customer.domain.CustomerState;
import edu.obya.blueprint.customer.web.CustomerSummary;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CustomerCucumberTypeRegistryConfigurer {

    @ParameterType("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")
    public CustomerId customerId(String s) {
        return CustomerId.parse(s);
    }

    @ParameterType("\\d{4}(.\\d{2}){2}(T)(\\d{2}.){2}\\d{2}")
    public LocalDateTime timestamp(String s) {
        return LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @ParameterType("OK|CREATED|UNAUTHORIZED|FORBIDDEN|BAD_REQUEST|NOT_FOUND|NO_CONTENT")
    public HttpStatus status(String s) {
        return HttpStatus.valueOf(s);
    }

    @DataTableType
    public Customer customerEntryTransformer(Map<String, String> entries) {
        return Customer.builder()
                .id(CustomerId.parse(entries.get("id")))
                .state(CustomerState.builder()
                    .firstName(entries.get("first-name"))
                    .lastName(entries.get("last-name"))
                    .build())
                .build();
    }

    @DataTableType
    public CustomerSummary customerSummaryTransformer(Map<String, String> entries) {
        return CustomerSummary.builder()
                .id(entries.get("id"))
                .fullName(entries.get("full-name"))
                .firstName(entries.get("first-name"))
                .lastName(entries.get("last-name"))
                .build();
    }

    @DataTableType
    public CustomerDto customerDtoEntryTransformer(Map<String, String> entries) {
        return CustomerDto.builder()
                .firstName(entries.get("first-name"))
                .lastName(entries.get("last-name"))
                .build();
    }

    @DataTableType
    public CustomerId customerIdEntryTransformer(Map<String, String> entries) {
        return CustomerId.parse(entries.get("id"));
    }
}
