# arch blueprint java
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
- Build the project with `./gradlew clean build`.
- Launch the tests suite with `./gradlew clean check`.
- Start the database with `docker-compose up`.
- Launch the application with `./gradlew bootRun --args='--spring.profiles.active=test,jpa,postgres'`.
- Play use cases in Postman using [this default Postman collection](https://vondacho.github.io/arch-blueprint-java/postman/postman_collection.json).

## Release
Draft new release of the application from GitHub [release panel](https://github.com/vondacho/arch-blueprint-java/releases).

## This documentation
- Powered by [MkDocs](https://www.mkdocs.org/getting-started/)
- API documentation powered by [Swagger UI](https://swagger.io/tools/swagger-ui/)
- Architecture documentation powered by [Structurizr](https://structurizr.com/) and [AppMap](https://appmap.io/docs/appmap-overview.html)
