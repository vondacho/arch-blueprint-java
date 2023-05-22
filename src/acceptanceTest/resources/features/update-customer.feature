@txn
Feature: To update an existing customer

  Background:
    Given user has write permission
    And a following set of existing customers
      | id                                   | first-name | last-name |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | john       | doe       |
      | afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc | bob        | dylan     |

  Scenario: replace one customer
    When replacing existing customer ce751f30-217a-422c-b81b-8f75df4917b6
      | first-name | last-name |
      | elvis      | presley   |
    Then the response status is NO_CONTENT
    And the set of existing customers is the following
      | id                                   | first-name | last-name |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | elvis      | presley   |
      | afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc | bob        | dylan     |

  Scenario Outline: replace a customer with some invalid attributes
    When replacing existing customer <id>
      | first-name   | last-name   |
      | <first-name> | <last-name> |
    Then the response status is BAD_REQUEST
    And the set of existing customers is left unchanged
    Examples:
      | id                                   | first-name | last-name |
      | ce751f30-217a-422c-b81b-8f75df4917b6 |            |           |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | test       |           |
      | ce751f30-217a-422c-b81b-8f75df4917b6 |            | test      |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | bob        | dylan     |
