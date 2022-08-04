package com.eqonex.trading.steps;

import io.cucumber.java8.En;
import org.testng.asserts.SoftAssert;
import spot.MarketOrder;
import spot.SpotOrder;
import spot.User;

public class PlaceSpotOrderSteps implements En {
    User a;
    SpotOrder ord1;
    SoftAssert softAssert = new SoftAssert();

    public PlaceSpotOrderSteps() {
        Given("User has user type is {word}, feeTier is {int}, USDC balance is {double}, BTC balance is {double}", (String userType, Integer feeTier, Double quoteBalance, Double baseBalance) -> {
            a = new User(userType,feeTier,quoteBalance,baseBalance);
        });
        When("I place a {word} Market order with qty is {double} and markPrice is {double}", (String side, Double qty, Double markPrx) -> {
            ord1 = new MarketOrder(side,qty,markPrx);
            a.placeOrder(ord1);
        });
        And("the order is {word} at markPrice {double}", (String ordStt, Double filledPrx) -> {
            ord1.ordStt = ordStt;
            ord1.filledPrx = filledPrx;
            ord1.filledQty = ord1.qty;
        });

        Then("my USDC balance is updated correctly", () -> {
            a.updateQuoteBalancesWithOrder(ord1);
            a.updateAvailableQuoteBalanceWithOrder(ord1);

            softAssert.assertEquals(a.getQuoteBalance(),50000+ord1.getOrdNotional(),"quote balance is incorrect");

        });
        And("my BTC balance is updated correctly", () -> {
            a.updateBaseBalancesWithOrder(ord1);
            a.updateAvailableBaseBalanceWithOrder(ord1);

            softAssert.assertEquals(a.getBaseBalance(),4.0,"base balance is incorrect");
            softAssert.assertEquals(ord1.getOrderFees(ord1.markPrx, ord1.qty, a.getUserFeeRate()), 8.0, "Order Fee is incorrect");
            softAssert.assertEquals(a.getUserFeeRate(),0.0008,"User Fee Rate is incorrect");
            softAssert.assertAll();
        });
    }
}
