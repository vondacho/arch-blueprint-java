# Miscellaneous

## This documentation
Build technical documentation with `./gradlew allureAggregateReport plantumlAll generateSwaggerUI mkdocsBuild`.

## AppMap resources
Generate AppMap resources with `./gradlew appmap test`.

View your local AppMap resources following [this tutorial](https://github.com/vondacho/appmap-viewer#getting-started).

## Release
Draft new release of the application from GitHub [release panel](https://github.com/vondacho/arch-blueprint-java/releases).

## Test users

- `test / test` gives access to almost all endpoints but the delete and shutdown ones.
- `admin / admin` gives access to all functional and configured application management endpoints.

## Spring profiles

- `dev` defines credentials and access control for **test** and **admin** users.
- `test` defines credentials and access control for **anonymous**, **test** and **admin** users.
- `jpa` enables persistence with relational database using JPA interface.
- `postgres` configure datasource to use a local database running on port 5432.
