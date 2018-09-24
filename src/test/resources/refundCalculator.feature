Feature: 001
  In order to verify the elements
  in refund calculator page I want


  Background:
    Given Open the Firefox and launch the application


  Scenario: Calculator elements verification
    When I click on countries dropdown list
    And it is displayed
    Then I verifying that number of countries from the list equal 40
    And each country is spelled correctly
    And currency with initial calculator elements states are correct

  Scenario: Enter min acceptable for refund amount
    When I click on countries dropdown list
    And check refund calculation with min amount
#    Then I click on Add Purchase button and enter min amount
