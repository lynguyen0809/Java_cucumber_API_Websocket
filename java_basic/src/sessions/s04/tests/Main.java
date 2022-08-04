package sessions.s04.tests;

import sessions.s04.oop.SpotOrder;
import sessions.s04.oop.User;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        User a = new User("MAKER",2,1,10000);
        User b = new User("TAKER",2,2,15000);
        SpotOrder ord1 = new SpotOrder("sell",8000, 0.015);
        SpotOrder ord2 = new SpotOrder("buy",15000,1);
        SpotOrder ord3 = new SpotOrder("buy",5000,0.5);
        SpotOrder ord4 = new SpotOrder("BUy",5000,0.5);

        System.out.println("User Fee Rate is: " + df.format(a.getUserFeeRate()));
        a.placeOrder(ord1,ord2,ord3,ord4);
    }

    private static final DecimalFormat df = new DecimalFormat("0.00000");
}
