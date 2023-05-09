# Architecture

## C4
- [System context](https://www.structurizr.com/share/38199/diagrams#blueprint-context)
- [Container view](https://www.structurizr.com/share/38199/diagrams#blueprint-containers)
- [Component view](https://www.structurizr.com/share/38199/diagrams#blueprint-api-components)

## AppMap

### From your local machine
To start the AppMap viewer web application on local port 3000 with
`docker run -it -p 3000:8080 ghcr.io/vondacho/appmap-viewer:latest`.

To visualize the behaviour of main use cases from the browser at
`http://localhost:3000/appmap/appmap.html?appmap=<url_to_your_AppMap_file>`.

- [E2E from API layer](http://localhost:3000/appmap/appmap.html?appmap=https://vondacho.github.io/arch-blueprint-java/appmap/edu_obya_blueprint_customer_adapter_rest_CustomerEndpointIT_shouldCreateAndModifyAndDeleteCustomer.appmap.json)
- [E2E from Service layer](http://localhost:3000/appmap/appmap.html?appmap=https://vondacho.github.io/arch-blueprint-java/appmap/edu_obya_blueprint_customer_application_CustomerServiceIT_shouldCreateAndFindAndModifyAndRemoveACustomer.appmap.json)
- [E2E from Data layer](http://localhost:3000/appmap/appmap.html?appmap=https://vondacho.github.io/arch-blueprint-java/appmap/edu_obya_blueprint_customer_adapter_jpa_CustomerRepositoryIT_shouldCreateAndFindAndModifyAndRemoveACustomer.appmap.json)

#### Local AppMap file
To start the AppMap viewer web application on local port 3000 with
`docker run -it -p 3000:8080 -v $(pwd):/usr/appmap-viewer/maps ghcr.io/vondacho/appmap-viewer:latest`.

To visualize your local AppMap file from the browser at
`http://localhost:3000/appmap/appmap.html?appmap=/maps/<your_AppMap_file>`.

### From your IDE
- To install the AppMap extension in your IDE.
- To build the app maps with `./gradlew appmap test`.
- To visualize the app maps using the AppMap extension inside your IDE.

## Hexagonal
The logical layers are organized like an onion with the domain layer at the center.
Infrastructure layer adapts the output ports exposed by the application and domain layers.
Web layer consumes input ports exposed by the application layer.
Application layer acts as a use case orchestrator and consumes input ports exposed by the domain layer.
Models are segregated and mapped between the different layers.
ACL is used to protect the internal ubiquitous language from external languages.

![Domain model](../uml/hexagonal.svg)

## Domain model
![Domain model](../uml/domain-model.svg)

## Data model
![Data model](../uml/data-model.svg)

## CI/CD
[Github actions](https://github.com/vondacho/arch-blueprint-java/actions)
