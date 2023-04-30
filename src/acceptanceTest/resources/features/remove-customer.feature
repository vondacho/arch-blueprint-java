@txn
Feature: To remove one existing customer

  Background:
    Given user has remove permission
    And a following set of existing customers
      | id                                   | first-name | last-name |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | john       | doe       |
      | afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc | bob        | dylan     |

  Scenario: customer does exist
    When removing existing customer ce751f30-217a-422c-b81b-8f75df4917b6
    Then the response status is NO_CONTENT
    And the set of existing customer ids is the following
      | id                                   |
      | afd9ce9f-ee0e-4547-8c77-3cc43ec85dbc |

  Scenario: customer does not exist
    When removing existing customer cef60f2c-f5eb-4e2e-8f4b-d1e5dd8e9243
    Then the response status is NOT_FOUND
    And the set of existing customers is left unchanged
