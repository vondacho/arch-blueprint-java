@txn
Feature: To list one or more customers

  Background:
    Given user has read permission
    And a following set of existing customers
      | id                                   | first-name | last-name |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | john       | doe       |
      | afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc | bob        | dylan     |

  Scenario: List all customers
    When listing all existing customers
    Then the response status is OK
    And the response contains the following customers
      | id                                   | first-name | last-name | full-name |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | john       | doe       | john doe  |
      | afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc | bob        | dylan     | bob dylan |

  Scenario: Show one customer
    When getting existing customer ce751f30-217a-422c-b81b-8f75df4917b6
    Then the response status is OK
    And the response contains only the following customer
      | id                                   | first-name | last-name | full-name |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | john       | doe       | john doe  |
