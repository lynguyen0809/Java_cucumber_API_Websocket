package com.eqonex.trading.steps;

import com.eqonex.rest.RESTClient;
import com.eqonex.trading.object.TradeUser;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.Assert;

import static org.hamcrest.Matchers.*;

public class LogonSteps extends _BaseStep implements En {
    public LogonSteps() {
        String url = "https://eqo-uat.com/api/logon/";

        Given("User is at Trading UI", () -> {
            System.out.println("User is at Trading UI");
        });

        When("User logins successfully without 2FA", () -> {
            TradeUser tradeUser = getGlobalResource().load(TradeUser.class);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login",tradeUser.getUserEmail());
            jsonObject.put("password", tradeUser.getUserPassWord());

            RESTClient request = new RESTClient();
            Response response = request.POST(url,jsonObject.toString());
            getGlobalResource().save(Response.class,response);

            // Verify user login successfully
            response.then().assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .body("id", equalTo(tradeUser.getUserId()));
            System.out.println("The response after User logins successfully without 2FA" + response.asPrettyString());
        });

        When("User logins successfully with MFA", () -> {
            TradeUser tradeUser = getGlobalResource().load(TradeUser.class);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login",tradeUser.getUserEmail());
            jsonObject.put("password", tradeUser.getUserPassWord());
            jsonObject.put("code",tradeUser.getOtpCode());

            RESTClient request = new RESTClient();
            Response response = request.POST(url,jsonObject.toString());
            getGlobalResource().save(Response.class,response);

            // Verify user login successfully
            response.then().assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .body("id", equalTo(tradeUser.getUserId()));
            System.out.println("The response after User logins successfully with 2FA" + response.asPrettyString());
        });

        Then("the response contains requestToken and requestSecret correctly", () -> {
            TradeUser tradeUser = getGlobalResource().load(TradeUser.class);
            Response response = getGlobalResource().load(Response.class);

            Assert.assertEquals(response.body().jsonPath().get("requestToken"),tradeUser.getRequestToken(),"requestToken is incorrect");
            Assert.assertEquals(response.body().jsonPath().get("requestSecret"),tradeUser.getRequestSecret(),"secretToken is incorrect");
        });

        And("the cookies respond correctly", () -> {
            TradeUser tradeUser = getGlobalResource().load(TradeUser.class);
            Response response = getGlobalResource().load(Response.class);

            Assert.assertNotNull(response.cookies(), "cookies are not returned");
            Assert.assertTrue(response.getCookies().containsKey("diginex-onboarding-access-uat"),"expected key is NOT returned");
            Assert.assertNotNull(response.cookies().get("diginex-onboarding-access-uat"),"access cookie is null unexpectedly");
            Assert.assertNotNull(response.cookies().get("diginex-onboarding-refresh-uat"),"refresh cookie is null unexpectedly");
            Assert.assertNotNull(response.cookies().get("diginex-onboarding-user-id-uat"),"refresh cookie is null unexpectedly");
            System.out.println("User cookies after login: \n" +
                                "diginex-onboarding-access-uat: " + response.cookies().get("diginex-onboarding-access-uat") + ",\n" +
                                "diginex-onboarding-refresh-uat: " + response.cookies().get("diginex-onboarding-refresh-uat") + ",\n" +
                                "diginex-onboarding-user-id-uat: " + response.cookies().get("diginex-onboarding-user-id-uat"));
            System.out.println("----------------------------------------");
        });

        When("User logins with invalid {word}", (String value) -> {
            TradeUser tradeUser = getGlobalResource().load(TradeUser.class);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", tradeUser.getUserEmail());
            jsonObject.put("password", tradeUser.getUserPassWord());
            jsonObject.put("code", tradeUser.getOtpCode());

            switch (value){
                case "email":
                    jsonObject.put("login", tradeUser.getUserEmail()+"com");
                    break;
                case "password":
                    jsonObject.put("password", tradeUser.getUserPassWord()+"12");
                    break;
                case "code":
                    jsonObject.put("code",tradeUser.getOtpCode()+1);
                    break;
            }

            RESTClient request = new RESTClient();
            Response response = request.POST(url,jsonObject.toString());
            System.out.println((String) response.getBody().jsonPath().get("error"));
            if (response.getBody().jsonPath().get("error").equals("Two-factor authentication code invalid")) {
                int i = 0;
                while (i < 4)
                {
                    if(value.equals("email") || value.equals("password")){
                        Thread.sleep(10000);
                        String newOtpCode = tradeUser.getOtpCode();
                        jsonObject.put("code", newOtpCode);
                        response = request.POST(url, jsonObject.toString());
                        i++;
                    } else break;
                }
            } else System.out.println("2FA code is as expectation");
            getGlobalResource().save(Response.class,response);
        });

        Then("User failed to login with {string}", (String errMsg) -> {
            TradeUser tradeUser = getGlobalResource().load(TradeUser.class);
            Response response = getGlobalResource().load(Response.class);

            Assert.assertEquals(response.body().jsonPath().get("error"),errMsg,"Incorrect error message returned");
        });
    }
}
