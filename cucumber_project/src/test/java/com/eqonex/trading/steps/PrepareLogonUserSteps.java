package com.eqonex.trading.steps;

import com.eqonex.trading.object.TradeUser;
import io.cucumber.java8.En;

public class PrepareLogonUserSteps extends _BaseStep implements En {
    public PrepareLogonUserSteps(){
        Given("Login with user has no 2FA", () -> {
            TradeUser tradeUser = new TradeUser(120174,"ln_uat_indi_v3um6@mailinator.com","Eqonex@123456","","0KgQh7cSBIG0D6M9","Y572zPnIPMr75A8Ub3S7mC4Y");
            getGlobalResource().save(TradeUser.class,tradeUser);
        });

        Given("Login with user has 2FA", () -> {
            TradeUser tradeUser = new TradeUser(119451,"ln_uat_indi_qyilg@mailinator.com","Eqonex@123456","QWU7EDAG7LB5EL36ON72RWHJ7YLZKXFLMVOATNJ4TZ47ZTSQW7CQ","DrIcBgvfo7eZtAiE","XYu38H64ukto4ponA4IX45rC");
            getGlobalResource().save(TradeUser.class,tradeUser);
        });
    }
}
