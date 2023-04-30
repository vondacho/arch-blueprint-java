package customer.cdc.spring.contracts.consumerB

import org.springframework.cloud.contract.spec.Contract

final AUTH_TOKEN_REGEX = "Basic (?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?"
final URI_WITH_ID_REGEX = "^/customers/[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}\$"

[
        Contract.make {
            name "should return a well-formatted existing customer"
            request {
                method GET()
                url(
                        value(
                                consumer(regex(URI_WITH_ID_REGEX)),
                                producer(execute("uriWithCustomerId()"))
                        )
                )
                headers {
                    header(authorization(),
                            value(
                                    consumer(regex(AUTH_TOKEN_REGEX)),
                                    producer(execute("userAuthToken()"))
                            )
                    )
                }
            }
            response {
                status OK()
                headers {
                    contentType(applicationJson())
                }
                body(
                        id: value(consumer(fromRequest().path(1))),
                        firstName: value(consumer(anyNonEmptyString())),
                        lastName: value(consumer(anyNonEmptyString())),
                        fullName: value(consumer(anyNonEmptyString()))
                )
                bodyMatchers {
                    jsonPath('$.id', byRegex(uuid()))
                    jsonPath('$.firstName', byRegex(nonEmpty()))
                    jsonPath('$.lastName', byRegex(nonEmpty()))
                    jsonPath('$.fullName', byRegex(nonEmpty()))
                }
            }
        }
]
