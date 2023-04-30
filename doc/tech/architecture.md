# Architecture

## Context

## Micro design

### Clean architecture

The logical layers are organized like an onion.

- **appl** (application logic, orchestration of the use cases, transactional): `- uses ->` _domain_
- **domain** (Domain model composed of core entities and core logic)
- **external** (External systems models and adapters) `- imports ->` _appl_, _domain_

## Domain model

## Implementation

### Stacks, libraries, frameworks

## Deployment

## CICD

[Github actions](https://github.com/vondacho/arch-blueprint-java/actions)