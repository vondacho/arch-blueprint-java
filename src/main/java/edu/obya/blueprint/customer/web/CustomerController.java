package edu.obya.blueprint.customer.web;

import edu.obya.blueprint.common.util.search.FindCriteria;
import edu.obya.blueprint.customer.appl.CustomerDto;
import edu.obya.blueprint.customer.appl.CustomerService;
import edu.obya.blueprint.customer.domain.CustomerId;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Timed("customers.summary")
@RequestMapping("/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService, MeterRegistry registry) {
        this.customerService = customerService;
        registry.timer("customers.summary");
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CustomerSummary> findByCriteria(@RequestParam(required = false) String filter, Pageable pageable) {
        return customerService
                .findByCriteria(StringUtils.hasText(filter) ? FindCriteria.from(filter) : FindCriteria.empty())
                .stream().map(CustomerSummary::from)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerSummary findById(@PathVariable String id) {
        return CustomerSummary.from(customerService.get(CustomerId.from(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerId create(@RequestBody CustomerDto customerDto) {
        return customerService.create(customerDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @RequestBody CustomerDto customerDto) {
        customerService.update(customerDto, CustomerId.from(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String id) {
        customerService.remove(CustomerId.from(id));
    }
}
