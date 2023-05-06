# arch-blueprint-java

![build workflow](https://github.com/vondacho/arch-blueprint-java/actions/workflows/build.yml/badge.svg)

A Java project as template and pedagogical support for the teaching of Clean Architecture crafting practice.

## Features

CRUD operations on Customer entities exposed by a REST API.

- Web request validation with Atlassian
- Web security based on Basic Authentication
- Application management with Spring Actuator
- Acceptance testing with Cucumber
- Contract testing with Pact and Spring Cloud Contract
- Architecture testing with ArchUnit

## Getting started

- To build the project with `./gradlew clean build`.
- To launch the test suite with `./gradlew clean check`.
- To launch the application with `./gradlew bootRun --args='--spring.profiles.active=test,jpa'`.

## Technical documentation

- Powered by MkDocs
- API documentation powered by Swagger UI
- Architecture documentation powered by Structurizr and AppMap
- [Latest release](https://vondacho.github.io/arch-blueprint-java)