# arch-blueprint-java
![build workflow](https://github.com/vondacho/arch-blueprint-java/actions/workflows/build.yml/badge.svg)

A Java project as template and pedagogical support for the teaching of Clean Architecture crafting practice.

## Features
CRUD operations on Customer entities exposed by a REST API.

- Web request validation with [Swagger request validator](https://bitbucket.org/atlassian/swagger-request-validator/src/master/)
- Web security based on Basic Authentication
- Exception handling with [Zalando problem handling](https://github.com/zalando/problem-spring-web)
- Application management with Spring Actuator
- Acceptance testing with [Cucumber](https://cucumber.io/docs/cucumber/)
- Contract testing with [Pact](https://docs.pact.io/) and [Spring Cloud Contract](https://softwaremill.com/contract-testing-spring-cloud-contract/)
- Architecture testing with [ArchUnit](https://www.archunit.org/motivation)

## Getting started
- Build the project with `./gradlew clean build`.
- Start the containerized database with `docker-compose up`.
- Launch the application locally with `./gradlew bootRun --args='--spring.profiles.active=test,jpa,postgres'`.
- Play use cases with Postman using [this default collection](https://vondacho.github.io/arch-blueprint-java/postman/postman_collection.json) or with [Swagger UI](https://vondacho.github.io/arch-blueprint-java/api/).

## Documentation
Find full detailed documentation [here](https://vondacho.github.io/arch-blueprint-java/) powered by [MkDocs](https://www.mkdocs.org/getting-started/)
