@txn
Feature: To create one new customer

  Background:
    Given user has write permission
    And a following set of existing customers
      | id                                   | first-name | last-name |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | john       | doe       |

  Rule: An existing customer is a persisted resource in the system.

    Scenario: Add a new customer to the existing customers
      Given the next identifier is afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc
      When registering the new customer
        | first-name | last-name |
        | bob        | dylan     |
      Then the response status is CREATED
      And the returned customer id is afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc
      And the set of existing customers is the following
        | id                                   | first-name | last-name |
        | ce751f30-217a-422c-b81b-8f75df4917b6 | john       | doe       |
        | afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc | bob        | dylan     |

  Rule: An existing customer has a valid set of attributes.

    Scenario Outline: Add a customer with some invalid attributes
      Given the next identifier is afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc
      When registering the new customer
        | first-name   | last-name   |
        | <first-name> | <last-name> |
      Then the response status is BAD_REQUEST
      And the set of existing customers is left unchanged

      Examples:
        | first-name | last-name |
        |            |           |
        | test       |           |
        |            | test      |
        | john       | doe       |
