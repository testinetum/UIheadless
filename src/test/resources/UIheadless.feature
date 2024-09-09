@tag
Feature: Authentication and Dropdown Interaction

@tag1
Scenario: Home Page Title Verification
    Given I am on the home page
    Then the page title should be "The Internet"

@tag2
Scenario Outline: Authentication with invalid credentials
    Given I navigate to the login page
    When I enter "<username>" and "<password>" and click login
    Then I should see the flash message

    Examples:
      | username  | password  |
      | username2 | password2 |
      | username3 | password3 |

@tag3
  Scenario: Authentication with valid credentials
    Given I navigate to the login page
    When I enter "tomsmith" and "SuperSecretPassword!" and click login
    Then I should see the logout link
    And I should see the flash message

@tag4
  Scenario: Dropdown selection
    Given I am on the dropdown page
    When I select the option with value "2"
    Then the selected option should be "2"

 