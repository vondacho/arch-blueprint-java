# arch-blueprint-java

![build workflow](https://github.com/vondacho/arch-blueprint-java/actions/workflows/build.yml/badge.svg)

A Java project as template and pedagogical support for the teaching of Clean Architecture crafting practice.

## Features

CRUD operations on Customer entities exposed by a REST API.

- Web request validation with [Swagger request validator](https://bitbucket.org/atlassian/swagger-request-validator/src/master/)
- Web security based on Basic Authentication
- Application management with Spring Actuator
- Acceptance testing with [Cucumber](https://cucumber.io/docs/cucumber/)
- Contract testing with [Pact](https://docs.pact.io/) and [Spring Cloud Contract](https://softwaremill.com/contract-testing-spring-cloud-contract/)
- Architecture testing with [ArchUnit](https://www.archunit.org/motivation)

## Getting started

- To build the project with `./gradlew clean build`.
- To launch the test suite with `./gradlew clean check`.
- To launch the application with `./gradlew bootRun --args='--spring.profiles.active=test,jpa'`.

## Technical documentation

- Powered by [MkDocs](https://www.mkdocs.org/getting-started/)
- API documentation powered by [Swagger UI](https://swagger.io/tools/swagger-ui/)
- Architecture documentation powered by [Structurizr](https://structurizr.com/) and [AppMap](https://appmap.io/docs/appmap-overview.html)
- [Latest release](https://vondacho.github.io/arch-blueprint-java)
