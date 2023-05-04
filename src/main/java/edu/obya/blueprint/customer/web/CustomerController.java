package edu.obya.blueprint.customer.web;

import com.structurizr.annotation.Component;
import com.structurizr.annotation.UsedByPerson;
import com.structurizr.annotation.UsesComponent;
import edu.obya.blueprint.common.util.search.FindCriteria;
import edu.obya.blueprint.customer.application.CustomerDto;
import edu.obya.blueprint.customer.application.CustomerService;
import edu.obya.blueprint.customer.domain.CustomerId;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.ResponseEntity.*;

@Component(description = "API for customer management", technology = "Java")
@UsedByPerson(name = "User", description = "Uses", technology = "HTTPS/REST/JWT")
@Timed("customers.summary")
@RequestMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CustomerController {

    @UsesComponent(description = "manages customer resources using", technology = "Java")
    @Autowired
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService, MeterRegistry registry) {
        this.customerService = customerService;
        registry.timer("customers.summary");
    }

    @GetMapping
    public ResponseEntity<List<CustomerSummary>> list(@RequestParam(required = false) String filter) {
        return ok(customerService
                .list(StringUtils.hasText(filter) ? FindCriteria.from(filter) : FindCriteria.empty())
                .stream().map(CustomerSummary::from)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerSummary> get(@PathVariable UUID id) {
        return ok(CustomerSummary.from(customerService.get(CustomerId.builder().id(id).build())));
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody CustomerDto customerDto) {
        return status(HttpStatus.CREATED)
                .header(CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .body(customerService.create(customerDto).getId().toString());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> replace(@PathVariable UUID id, @RequestBody CustomerDto customerDto) {
        customerService.update(customerDto, CustomerId.builder().id(id).build());
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable UUID id) {
        customerService.remove(CustomerId.builder().id(id).build());
        return noContent().build();
    }
}
