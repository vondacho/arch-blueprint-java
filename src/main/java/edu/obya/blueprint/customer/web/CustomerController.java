package edu.obya.blueprint.customer.web;

import edu.obya.blueprint.common.util.search.FindCriteria;
import edu.obya.blueprint.customer.appl.CustomerService;
import edu.obya.blueprint.customer.domain.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerDto findById(@PathVariable String id) {
        return customerService.get(CustomerId.from(id));
    }

    @GetMapping("/customers")
    @ResponseStatus(code = HttpStatus.OK)
    public List<CustomerDto> findByCriteria(@RequestParam(required = false) String filter, Pageable pageable) {
        return customerService.findByCriteria(
                StringUtils.hasText(filter) ? FindCriteria.from(filter) : FindCriteria.empty());
    }

    @PostMapping("/customers")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerId create(@RequestBody CustomerDto customerDto) {
        return customerService.create(customerDto);
    }

    @PutMapping("/customers/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @RequestBody CustomerDto customerDto) {
        customerService.update(customerDto, CustomerId.from(id));
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String id) {
        customerService.remove(CustomerId.from(id));
    }
}
