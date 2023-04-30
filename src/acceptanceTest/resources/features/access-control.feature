@txn
Feature: To allow only legitimate users to read and write data

  Background:
    Given a following set of existing customers
      | id                                   | first-name | last-name |
      | ce751f30-217a-422c-b81b-8f75df4917b6 | john       | doe       |

  Rule: Only authenticated users who have the read permission can read data

    Scenario: A user must be authenticated to read
      Given user is not authenticated
      When getting existing customer ce751f30-217a-422c-b81b-8f75df4917b6
      Then the response status is UNAUTHORIZED

    Scenario: A user with no permission cannot read
      Given user has no permission
      When getting existing customer ce751f30-217a-422c-b81b-8f75df4917b6
      Then the response status is FORBIDDEN

  Rule: Only authenticated users who have the write permission can write data

    Scenario: A user must be authenticated to create
      Given user is not authenticated
      When registering the new customer
        | first-name | last-name |
        | bob        | dylan     |
      Then the response status is UNAUTHORIZED

    Scenario: A user with no permission cannot create
      Given user has no permission
      When registering the new customer
        | first-name | last-name |
        | bob        | dylan     |
      Then the response status is FORBIDDEN

    Scenario: A user must be authenticated to modify
      Given user is not authenticated
      When replacing existing customer ce751f30-217a-422c-b81b-8f75df4917b6
        | first-name | last-name |
        | elvis      | presley   |
      Then the response status is UNAUTHORIZED

    Scenario: A user with no permission cannot modify
      Given user has no permission
      When replacing existing customer ce751f30-217a-422c-b81b-8f75df4917b6
        | first-name | last-name |
        | elvis      | presley   |
      Then the response status is FORBIDDEN

    Scenario: A user must be authenticated to remove
      Given user is not authenticated
      When removing existing customer ce751f30-217a-422c-b81b-8f75df4917b6
      Then the response status is UNAUTHORIZED

    Scenario: A user with no permission cannot remove
      Given user has no permission
      When removing existing customer ce751f30-217a-422c-b81b-8f75df4917b6
      Then the response status is FORBIDDEN

    Scenario: A user with write permission cannot remove
      Given user has write permission
      When removing existing customer ce751f30-217a-422c-b81b-8f75df4917b6
      Then the response status is FORBIDDEN
