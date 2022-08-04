Feature:

  @connect_wss
  Scenario: Connect wss type [6] orders
    Given Login with user has no 2FA
    And User gets order book info of BTC/USDC pair
    And user prepares a Spot BUY LIMIT order with 0.001 and price is out of MIC range
    When user connects wss type[6] orders
    And user places an order by api
    And the order is rejected by MIC
    Then verify order is rejected with status is 8 and reason FAILED_PRE_CREDIT_CHECK