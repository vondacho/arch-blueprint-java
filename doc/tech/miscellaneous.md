# Miscellaneous

## This documentation
Build technical documentation with `./gradlew allureAggregateReport plantumlAll generateSwaggerUI mkdocsBuild`.

## Publication to Structurizr cloud
- To have a free account on https://structurizr.com/help/getting-started
- To run:
  ```
  java \
  -cp ./build/libs/arch-blueprint-java.jar \
  -Dloader.main=edu.obya.blueprint.customer.c4.BlueprintC4Model \
  org.springframework.boot.loader.PropertiesLauncher \
  ./src/c4/c4-blueprint-java.dsl \
  <workspace-id>
  ```
- To visualize your architecture model at https://structurizr.com/

## Map components flows
Record components flows with `./gradlew appmap test`.

Then, view your local AppMap JSON resources following [this tutorial](https://github.com/vondacho/appmap-viewer#getting-started).

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
