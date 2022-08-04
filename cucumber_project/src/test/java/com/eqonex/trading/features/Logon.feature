Feature: Logon

  @login_without2FA
  Scenario: Login successfully without 2FA
    Given Login with user has no 2FA
    When User logins successfully without 2FA
    Then the response contains requestToken and requestSecret correctly
    And the cookies respond correctly

  @login_with2FA
  Scenario: Login successfully with 2FA
    Given Login with user has 2FA
    When User logins successfully with MFA
    Then the response contains requestToken and requestSecret correctly
    And the cookies respond correctly

  @login_withInvalidValues
  Scenario Outline: Login unsuccessfully with invalid <value>
    Given Login with user has 2FA
    When User logins with invalid <value>
    Then User failed to login with "<error_message>"
    Examples:
      |value    | error_message                              |
      |email    | Please enter a valid email and/or password.|
      |password | Please enter a valid email and/or password.|
      |code     | Two-factor authentication code invalid     |




