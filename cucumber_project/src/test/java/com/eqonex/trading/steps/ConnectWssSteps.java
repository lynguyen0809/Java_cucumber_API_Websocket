package com.eqonex.trading.steps;

import com.eqonex.trading.object.ISignatureGenerator;
import com.eqonex.trading.object.TradeUser;
import com.eqonex.wss.WssClient;
import io.cucumber.java8.En;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ConnectWssSteps extends _BaseStep implements En, ISignatureGenerator {
    public ConnectWssSteps(){
        When("user connects wss type[{int}] orders", (Integer type) -> {
            TradeUser tradeUser = getGlobalResource().load(TradeUser.class);

            List<Integer> wssTypes = new ArrayList<>();
            wssTypes.add(type);
            String event = "S";
            Integer account = tradeUser.getUserId();
            String requestId = event + "-" + new Date().getTime();
            String password_str = event + account + type + requestId;
            String password_signature = ISignatureGenerator.signatureGenerator(tradeUser.getRequestSecret(),password_str);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("requestId", requestId);
            jsonObject.put("types",wssTypes);
            jsonObject.put("event",event);
            jsonObject.put("username",tradeUser.getRequestToken());
            jsonObject.put("password",password_signature);
            jsonObject.put("account", account);
            jsonObject.put("useApiKeyForAuth", true);

            WssClient orderWssClient = new WssClient(jsonObject.toString(),"");
            orderWssClient.openAndTillConnected();
            getGlobalResource().save(WssClient.class,orderWssClient);
        });
        Then("verify order is rejected with status is {int} and reason {word}", (Integer ordStt, String rejectReason) -> {
            WssClient orderWssClient = getGlobalResource().load(WssClient.class);
            Response resOrder = getGlobalResource().load(Response.class);
            String orderId = resOrder.getBody().jsonPath().getString("orderId");

//            JSONArray ordMsg = orderWssClient.getMessageThatContain(orderId);
//            getGlobalResource().save(JSONArray.class, ordMsg);
//            System.out.println("Filtered Message: " + ordMsg);

            List<String> filterList = orderWssClient.getMessageContain(orderId);
            System.out.println("Filter Orders: " + filterList);

            SoftAssert softAssert = new SoftAssert();
//            for(int i = 0; i < ordMsg.length(); i++){
            for (String msg : filterList) {
//                JSONObject ordInfo = new JSONObject(msg);
                JsonPath orders = JsonPath.from(msg);
                System.out.println(orders.getString("orders.find{ord -> ord.orderUpdateSeq = 1}.orderRejectReasonEnum"));

//                JSONArray orders = ordInfo.getJSONArray("orders");
//                for (Object o : orders) {
//                    JSONObject ord = (JSONObject) o;
//                    if (ord.keySet().contains("orderRejectReasonEnum")) {
//                        softAssert.assertEquals(ord.getString("orderRejectReasonEnum"),
//                                rejectReason, "Incorrect rejected reason");
//                        System.out.println(
//                                "Reject reason is: " + ord.getString("orderRejectReasonEnum"));
//                    }
//                    if (ord.keySet().contains("ordStatus")
//                            && ord.getInt("orderUpdateSeq") == 1) {
//                        softAssert.assertEquals(ord.getString("ordStatus"), ordStt.toString(),
//                                "Incorrect order status");
//                        System.out.println("Order status is: " + ord.getString("ordStatus"));
//                    }
//                }
            }
            softAssert.assertAll();
        });

    }
}
