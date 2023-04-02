package edu.obya.blueprint.customer.web;

import edu.obya.blueprint.customer.domain.CustomerAlreadyExistException;
import edu.obya.blueprint.customer.domain.CustomerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.AdviceTrait;

import java.net.URI;

public interface CustomerAdviceTrait extends AdviceTrait {

    @ExceptionHandler
    default ResponseEntity<Problem> handleThrowable(
            final CustomerNotFoundException exception,
            final NativeWebRequest request) {
        return create(
                exception,
                Problem.builder()
                        .withType(URI.create("https://obya.edu/not-found"))
                        .withStatus(Status.NOT_FOUND)
                        .withTitle("Customer not found")
                        .withDetail(exception.getMessage())
                        .build(),
                request);
    }

    @ExceptionHandler
    default ResponseEntity<Problem> handleThrowable(
            final CustomerAlreadyExistException exception,
            final NativeWebRequest request) {
        return create(
                exception,
                Problem.builder()
                        .withType(URI.create("https://obya.edu/already-exist"))
                        .withStatus(Status.BAD_REQUEST)
                        .withTitle("Customer exists")
                        .withDetail(exception.getMessage())
                        .build(),
                request);
    }
}
