package edu.obya.blueprint;

import edu.obya.blueprint.customer.web.CustomerAdviceTrait;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.OpenApiValidationAdviceTrait;

@ControllerAdvice
public class ExceptionHandling implements
        ProblemHandling,
        OpenApiValidationAdviceTrait,
        CustomerAdviceTrait,
        SecurityAdviceTrait {
}
