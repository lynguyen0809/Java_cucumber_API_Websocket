package com.eqonex.trading.steps;

import com.eqonex.rest.RESTClient;
import com.eqonex.trading.object.ISignatureGenerator;
import com.eqonex.trading.object.TradeUser;
import io.cucumber.java8.En;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import spot.LimitOrder;
import spot.SpotOrder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static spot.UtilFunctions.truncate;

public class PlaceARejectOrderSteps extends _BaseStep implements En, ISignatureGenerator {
    public PlaceARejectOrderSteps(){
        Given("^User gets order book info of BTC/USDC pair$", () -> {
            String url = "https://trading-api.dexp-uat.com/api/getOrderBook";
            Integer pairId = 52;

            RESTClient request = new RESTClient();
            Response resOrderbook = request.GET(url, "pairId", pairId );
            getGlobalResource().save(Response.class,resOrderbook);

            Assert.assertEquals(resOrderbook.getStatusCode(),200, "Failed to get orderbook");
            Assert.assertNotNull(resOrderbook.body().jsonPath().get("usdMark"),"Mark Price is not returned");
        });
        And("user prepares a Spot {word} {word} order with {double} and price is out of MIC range", (String side, String ordType, Double qty) -> {
            Response resOrderbook = getGlobalResource().load(Response.class);
            double markPrice = resOrderbook.body().jsonPath().getDouble("usdMark");
            double prx = truncate(markPrice*(1+0.2),2);
            SpotOrder order = new LimitOrder(side,prx,qty);
            getGlobalResource().save(SpotOrder.class,order);
        });
        When("user places an order by api", () -> {
            String url = "https://eqo-uat.com/api/order/";
            TradeUser tradeUser = getGlobalResource().load(TradeUser.class);
            SpotOrder order = getGlobalResource().load(SpotOrder.class);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("instrumentId", 52);
            jsonObject.put("userId", tradeUser.getUserId());
            jsonObject.put("side", order.getOrdSide());
            jsonObject.put("ordType", 2);
            jsonObject.put("quantity", order.qty * Math.pow(10,6));
            jsonObject.put("quantity_scale", 6);
            jsonObject.put("timeInForce", 1);
            jsonObject.put("price", order.prx * Math.pow(10,2));
            jsonObject.put("price_scale", 2);
            jsonObject.put("blockWaitAck", 1);
            jsonObject.put("account", tradeUser.getUserId());

            String signature = ISignatureGenerator.signatureGenerator(tradeUser.getRequestSecret(),jsonObject.toString());

            Header h1 = new Header("requesttoken",tradeUser.getRequestToken());
            Header h2 = new Header("signature",signature);
            List<Header> headerList = new ArrayList<Header>();
            headerList.add(h1);
            headerList.add(h2);

            Headers headers = new Headers(headerList);
            RESTClient request = new RESTClient();
            Response resOrder = request.POST(url, headers, jsonObject.toString());
            getGlobalResource().save(Response.class,resOrder);

            resOrder.prettyPrint();
            Assert.assertTrue(resOrder.asString().contains("clOrdId"));
        });
        And("^the order is rejected by MIC$", () -> {
        });
        Then("verify order is rejected by {word} with {string}", (String reason, String msg) -> {
            String url = "https://eqo-uat.com/api/getOrderStatus";
            Response resOrder = getGlobalResource().load(Response.class);
            TradeUser tradeUser = getGlobalResource().load(TradeUser.class);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderId", resOrder.body().jsonPath().getInt("orderId"));
            jsonObject.put("userId", tradeUser.getUserId());
            jsonObject.put("account", tradeUser.getUserId());

            String signature = ISignatureGenerator.signatureGenerator(tradeUser.getRequestSecret(),jsonObject.toString());

            Header h1 = new Header("requesttoken",tradeUser.getRequestToken());
            Header h2 = new Header("signature",signature);
            List<Header> headerList = new ArrayList<Header>();
            headerList.add(h1);
            headerList.add(h2);

            Headers headers = new Headers(headerList);
            RESTClient request = new RESTClient();
            Response resOrderStt = request.POST(url, headers, jsonObject.toString());

            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(resOrderStt.asString().contains("orderId"), "OrderId is not contained in the response");
            softAssert.assertEquals(resOrderStt.body().jsonPath().getInt("ordStatus"),8,"Order status is not rejected");

            LinkedHashMap businessRejectObj = resOrderStt.getBody().jsonPath().getJsonObject("businessReject");
            JSONObject jsonString = new JSONObject(businessRejectObj);

            softAssert.assertEquals(jsonString.getString("reason"), reason, "Order is failed but NOT by MIC");
            softAssert.assertEquals(jsonString.getString("text"), msg, "Toast message is incorrect");
            softAssert.assertAll();
        });

    }
}
