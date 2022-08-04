package sessions.s04.oop;

import java.math.BigDecimal;
import java.util.HashMap;

public class User {
    Integer feeTier;
    String userType;

    //suggestion using BigDecimal
    //or Apply tolerance (0.000001)
    double baseBalance;
    double quoteBalance;
    double userFeeRate;
    double ordFee;

    public User(String userType, Integer feeTier, double baseBalance, double quoteBalance){
        this.userType = userType;
        this.feeTier = feeTier;
        this.baseBalance = baseBalance;
        this.quoteBalance = quoteBalance;
    };

    public double getUserFeeRate(){
        HashMap<Integer, Double[]> spotFees = new HashMap<Integer, Double[]>();
        spotFees.put(0, new Double[] {0.08/100, 0.09/100});
        spotFees.put(1, new Double[] {0.07/100, 0.08/100});
        spotFees.put(2, new Double[] {0.055/100, 0.065/100});
        spotFees.put(3, new Double[] {0.04/100, 0.05/100});
        spotFees.put(4, new Double[] {0.02/100, 0.03/100});
        spotFees.put(5, new Double[] {0.01/100, 0.02/100});

        if(userType.equals("MAKER")){
            return userFeeRate = spotFees.get(feeTier)[0];
        } else if(userType.equals("TAKER")){
            return userFeeRate = spotFees.get(feeTier)[1];
        } else {
            return userFeeRate = 0;
        }
    }
    public double getBaseBalance(){
        return this.baseBalance;
    }

    public double getQuoteBalance(){
        return this.quoteBalance;
    }

    public void placeOrder(SpotOrder... ord){
        int i = 1;
        for(SpotOrder a: ord){
            double ordNotional = a.getOrdNotional();
            double initialQuoteBalance = getQuoteBalance();
            double initialBaseBalance = getBaseBalance();

            if(a.side.equals("buy")){
                if(initialQuoteBalance >= ordNotional){
                    ordFee = truncate(getUserFeeRate() * ordNotional,6);
                    quoteBalance = initialQuoteBalance - ordNotional - ordFee;
                    baseBalance = initialBaseBalance + a.qty;

                    System.out.println("After order " + i + " ful-filled:");
                    System.out.println("Order Fee: " + ordFee);
                    System.out.println("Base Balance: " + baseBalance);
                    System.out.println("Quote Balance: " + quoteBalance);
                    System.out.println("===========================");
                }
                else {
                    System.out.println("Order " + i + " can't place since user has Insufficient fund");
                    System.out.println("===========================");
                }
            } else if(a.side.equals("sell")) {
                if(initialBaseBalance >= a.qty){
                    ordFee = truncate(getUserFeeRate() * ordNotional,6);
                    quoteBalance = initialQuoteBalance + ordNotional - ordFee;
                    baseBalance = initialBaseBalance - a.qty;

                    System.out.println("After order " + i + " ful-filled:");
                    System.out.println("Order Fee: " + ordFee);
                    System.out.println("Base Balance: " + baseBalance);
                    System.out.println("Quote Balance: " + quoteBalance);
                    System.out.println("===========================");
                } else {
                    System.out.println("Order " + i + " can't place since user has Insufficient fund");
                    System.out.println("===========================");
                }
            } else {System.out.println("Please input correct order side");}
            i++;
        }
    }

    static double truncate(double value, int decimalpoint)
    {

        // Using the pow() method
        value = value * Math.pow(10, decimalpoint);
        value = Math.floor(value);
        value = value / Math.pow(10, decimalpoint);

        return value;
    }

    private static BigDecimal truncateDecimal(double x, int numberofDecimals)
    {
        if ( x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }
}
