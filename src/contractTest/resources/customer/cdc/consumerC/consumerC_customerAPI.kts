package customer.contracts.consumerB

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

val AUTH_TOKEN_REGEX = "Basic (?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?"
val URI_WITH_ID_REGEX = "^/customers/[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}\$"

arrayOf(
    contract {
            name = "should return a well-formatted existing customer"
            request {
                method = GET
                urlPath = path(
                    value(
                        consumer(regex(URI_WITH_ID_REGEX)),
                        producer(execute("uriWithCustomerId()"))
                    )
                )
                headers {
                    authorization = value(
                        consumer(regex(AUTH_TOKEN_REGEX)),
                        producer(execute("userAuthToken()"))
                    )
                }
            }
            response {
                status = OK
                headers {
                    contentType = APPLICATION_JSON
                }
                body(mapOf(
                        "id" to consumer(fromRequest().path(1)),
                        "firstName" to consumer(anyNonEmptyString),
                        "lastName" to consumer(anyNonEmptyString),
                        "fullName" to consumer(anyNonEmptyString)
                ))
                bodyMatchers {
                    jsonPath("$.id", byRegex(uuid))
                    jsonPath("$.firstName", byRegex(nonEmpty))
                    jsonPath("$.lastName", byRegex(nonEmpty))
                    jsonPath("$.fullName", byRegex(nonEmpty))
                }
            }
        }
)
