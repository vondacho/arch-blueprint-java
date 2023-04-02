package edu.obya.blueprint.customer.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.validation.OpenApiValidationAdviceTrait;

@ControllerAdvice
public class CustomerExceptionHandling implements
        ProblemHandling,
        OpenApiValidationAdviceTrait,
        CustomerAdviceTrait {
}
