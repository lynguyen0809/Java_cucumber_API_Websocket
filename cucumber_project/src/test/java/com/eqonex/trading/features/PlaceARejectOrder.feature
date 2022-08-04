Feature:

  @spot_rejected_order
  Scenario Outline: Place a rejected order
    Given Login with user has no 2FA
    And User gets order book info of BTC/USDC pair
    And user prepares a Spot BUY LIMIT order with 0.001 and price is out of MIC range
    When user places an order by api
    And the order is rejected by MIC
    Then verify order is rejected by <reason> with "<message>"

    Examples:
      | reason                   | message
      | FAILED_PRE_CREDIT_CHECK  | Order rejected, upper limit (c. $9,691.47) exceeded

