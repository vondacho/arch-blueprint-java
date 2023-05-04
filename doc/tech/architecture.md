# Architecture

## C4
- [System context](https://www.structurizr.com/share/38199/diagrams#blueprint-context)
- [Container view](https://www.structurizr.com/share/38199/diagrams#blueprint-containers)
- [Component view](https://www.structurizr.com/share/38199/diagrams#blueprint-api-components)

## AppMap
- [App maps](../appmap/junit)
- To install the AppMap extension in your IDE
- To build the app maps with `./gradlew appmap test`

## Hexagonal
The logical layers are organized like an onion.

- **web** (web api): `- uses ->` _appl_, _domain_
- **appl** (application logic, orchestration of the use cases, transactional): `- uses ->` _domain_
- **domain** (Domain model composed of core entities and core logic)
- **infra** (External systems models and adapters) `- imports ->` _appl_, _domain_

## Domain model
![Domain model](../uml/domain.svg)

## Data model
![Data model](../uml/data.svg)

## CI/CD
[Github actions](https://github.com/vondacho/arch-blueprint-java/actions)