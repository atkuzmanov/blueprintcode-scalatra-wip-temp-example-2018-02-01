@statusHealth
Feature: Status health check

  Scenario: Status requested
    When I request the application status
    Then an OK status is returned

  Scenario: Serve static file
    When I request a static file
    Then the static file is returned

  Scenario: Return resource not found for unknown file
    When I request a static file that doesn't exist
    Then I receive a not found response
