Feature: Place Spot Order
  Background:
    User is at Trading UI

    @spot_market
  Scenario: as a normal user, I can place a Market order so that I can see my balances updated correctly
    Given User has user type is TAKER, feeTier is 1, USDC balance is 50000, BTC balance is 5
    When I place a SELL Market order with qty is 1 and markPrice is 10000
    And the order is full-filled at markPrice 10000
    Then my USDC balance is updated correctly
    And my BTC balance is updated correctly