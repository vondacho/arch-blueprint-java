package customer.cdc.spring.contracts.consumerA

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
                        id: value(fromRequest().path(1)),
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
        },

        Contract.make {
            name "should return the id of created customer"
            request {
                method POST()
                url(value("/customers"))
                headers {
                    header(authorization(),
                            value(
                                    consumer(regex(AUTH_TOKEN_REGEX)),
                                    producer(execute("userAuthToken()"))
                            )
                    )
                    contentType(applicationJson())
                }
                body(
                        firstName: value(producer(anyNonEmptyString())),
                        lastName: value(producer(anyNonEmptyString()))
                )
                bodyMatchers {
                    jsonPath('$.firstName', byRegex(nonEmpty()))
                    jsonPath('$.lastName', byRegex(nonEmpty()))
                }
            }
            response {
                status CREATED()
                headers {
                    contentType(textPlain())
                }
                body(
                        value(consumer(anyUuid()))
                )
                bodyMatchers {
                    byRegex(uuid())
                }
            }
        },

        Contract.make {
            name "should replace the existing customer at given id"
            request {
                method PUT()
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
                    contentType(applicationJson())
                }
                body(
                        firstName: value(producer(anyNonEmptyString())),
                        lastName: value(producer(anyNonEmptyString()))
                )
                bodyMatchers {
                    jsonPath('$.firstName', byRegex(nonEmpty()))
                    jsonPath('$.lastName', byRegex(nonEmpty()))
                }
            }
            response {
                status NO_CONTENT()
            }
        },

        Contract.make {
            name "should remove the customer at given id"
            request {
                method DELETE()
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
                                    producer(execute("adminAuthToken()"))
                            )
                    )
                }
            }
            response {
                status NO_CONTENT()
            }
        }
]
