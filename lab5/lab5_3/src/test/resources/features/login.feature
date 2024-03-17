Feature: Login in practice site

  Scenario: Successful login
    When I navigate to "https://blazedemo.com/"
    And I select 'Paris' as departure and 'Cairo' as destination
    And I click Find Flights
    And I select the first flight
    And I type in my information
    And I click Purchase Flight
    Then I should be see the page title 'BlazeDemo Confirmation'

