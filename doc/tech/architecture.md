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
The logical layers are organized like an onion with the domain layer at the center.
Infrastructure layer adapts the output ports exposed by the application and domain layers.
Web layer consumes input ports exposed by the application layer.
Application layer acts as a use case orchestrator and consumes input ports exposed by the domain layer.
Models are segregated and mapped between the different layers.
ACL is used to protect the internal ubiquitous language from external languages.

![Domain model](../uml/hexagonal.svg)

## Domain model
![Domain model](../uml/domain.svg)

## Data model
![Data model](../uml/data.svg)

## CI/CD
[Github actions](https://github.com/vondacho/arch-blueprint-java/actions)