package spot;

import java.util.HashMap;

public class User {
    Integer feeTier;
    String userType;
    private double baseBalance;
    private double quoteBalance;
    private double userFeeRate;
    private double availBalanceOfQuoteCCY;
    private double availBalanceOfBaseCCY;

    public User(String userType, Integer feeTier, double quoteBalance, double baseBalance){
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

        if(userType.toUpperCase().equals("MAKER")){
            return userFeeRate = spotFees.get(feeTier)[0];
        } else if(userType.toUpperCase().equals("TAKER")){
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

    public double getAvailBalanceOfQuoteCCY(){ return availBalanceOfQuoteCCY = this.quoteBalance;}

    public double getAvailBalanceOfBaseCCY(){ return availBalanceOfBaseCCY = this.baseBalance;}

    public boolean isSufficientFundForBuyOrder(double reservedQuotedBalance){
        return getAvailBalanceOfQuoteCCY() >= reservedQuotedBalance;
    }

    public boolean isSufficientFundForSellOrder(double reservedBaseBalance){
        return getAvailBalanceOfBaseCCY() >= reservedBaseBalance;
    }

    public void placeOrder(SpotOrder... ord){
        int i = 1;
        for(SpotOrder a: ord){
            System.out.println("Order " + i + " placed: ");
            if(a.isOrderInfoValid()){
                double ordNotional = a.getOrdNotional();
                userFeeRate = getUserFeeRate();
                double ordFee = a.getOrderFees(a.prx, a.qty,userFeeRate);
                if(a.side.toLowerCase().equals("buy")){
                    double reservedBalanceforBuy = a.getReservedBalanceforBuy(ordNotional, ordFee);
                    if(isSufficientFundForBuyOrder(reservedBalanceforBuy)){
                        System.out.println("Order placed successfully");
                    } else System.out.println("Insufficient funds");
                }
                else if(a.side.toLowerCase().equals("sell")){
                    double reservedBalanceforSell = a.getReservedBalanceforSell(a.qty);
                    if(isSufficientFundForSellOrder(reservedBalanceforSell)){
                        System.out.println("Order placed successfully");
                    } else System.out.println("Insufficient funds");
                }
            }
            else {
                System.out.println("Order Info is incorrect, please correct");
                System.out.println("===========================");
            }
            i++;
        }
    }

    public void updateAvailableQuoteBalanceWithOrder(SpotOrder ord){
        double reservedQuoteBalance;
        double ordNotional = ord.getOrdNotional();
        userFeeRate = getUserFeeRate();
        double ordFee = ord.getOrderFees(ord.prx, ord.qty,userFeeRate);

        double remainQty = ord.qty - ord.filledQty;
        double remainNotional = ord.getFilledOrdNotional(remainQty,ord.prx);
        double remainOrdFee = ord.getOrderFees(ord.prx, remainQty, userFeeRate);

        if(ord.side.toLowerCase().equals("buy")){
            switch (ord.ordStt.toLowerCase()) {
                case "full-filled":
                    availBalanceOfQuoteCCY = getAvailBalanceOfQuoteCCY();
                    break;
                case "open":
                    reservedQuoteBalance = ord.getReservedBalanceforBuy(ordNotional, ordFee);
                    availBalanceOfQuoteCCY = getAvailBalanceOfQuoteCCY() - reservedQuoteBalance;
                    break;
                case "partial-filled":
                    reservedQuoteBalance = ord.getReservedBalanceforBuy(remainNotional, remainOrdFee);
                    availBalanceOfQuoteCCY = getAvailBalanceOfQuoteCCY() - reservedQuoteBalance;
                    break;
            }
        } else if(ord.side.toLowerCase().equals("sell")){
            availBalanceOfQuoteCCY = getAvailBalanceOfQuoteCCY();
        } else System.out.println("Please check your inputs again");

    }

    public void updateAvailableBaseBalanceWithOrder(SpotOrder ord){
        double remainQty = ord.qty - ord.filledQty;
        double reservedBaseBalance;

        if(ord.side.toLowerCase().equals("buy")){
            switch (ord.ordStt.toLowerCase()) {
                case "full-filled":
                    availBalanceOfBaseCCY = getAvailBalanceOfBaseCCY();
                    break;
                case "open":
                    reservedBaseBalance = ord.getReservedBalanceforSell(ord.qty);
                    availBalanceOfBaseCCY = getAvailBalanceOfBaseCCY() - reservedBaseBalance;
                    break;
                case "partial-filled":
                    reservedBaseBalance = ord.getReservedBalanceforSell(remainQty);
                    availBalanceOfBaseCCY = getAvailBalanceOfBaseCCY() - reservedBaseBalance;
                    break;
            }
        } else if(ord.side.toLowerCase().equals("sell")){
            availBalanceOfBaseCCY = getAvailBalanceOfBaseCCY();
        } else System.out.println("Please check your inputs again");
    }

    public void updateQuoteBalancesWithOrder(SpotOrder ord){

        double ordNotional = ord.getOrdNotional();
        userFeeRate = getUserFeeRate();
        double ordFee = ord.getOrderFees(ord.prx, ord.qty,userFeeRate);

        double ordFilledNotional = ord.getFilledOrdNotional(ord.filledQty,ord.filledPrx);
        double ordFilledFee = ord.getOrderFees(ord.filledQty,ord.filledPrx,userFeeRate);

        if(ord.side.toLowerCase().equals("buy")){
            switch (ord.ordStt.toLowerCase()) {
                case "full-filled":
                    quoteBalance = getQuoteBalance() - ordNotional - ordFee;
                    break;
                case "open":
                    quoteBalance = getQuoteBalance();
                    break;
                case "partial-filled":
                    quoteBalance = getAvailBalanceOfBaseCCY() - ordFilledNotional - ordFilledFee;
                    break;
            }
        } else if(ord.side.toLowerCase().equals("sell")){
            switch (ord.ordStt.toLowerCase()) {
                case "full-filled":
                    quoteBalance = getQuoteBalance() - ordNotional*(-1) - ordFee;
                    break;
                case "open":
                    quoteBalance = getQuoteBalance();
                    break;
                case "partial-filled":
                    quoteBalance = getAvailBalanceOfBaseCCY() - ordFilledNotional*(-1) - ordFilledFee;
                    break;
            }
        } else System.out.println("Please check your inputs again");
    }

    public void updateBaseBalancesWithOrder(SpotOrder ord){
        if(ord.side.toLowerCase().equals("buy")){
            switch (ord.ordStt.toLowerCase()) {
                case "full-filled":
                    baseBalance = getBaseBalance() + ord.qty;
                    break;
                case "open":
                    baseBalance = getBaseBalance();
                    break;
                case "partial-filled":
                    baseBalance = getBaseBalance() + ord.filledQty;
                    break;
            }
        } else if(ord.side.toLowerCase().equals("sell")){
            switch (ord.ordStt.toLowerCase()) {
                case "full-filled":
                    baseBalance = getBaseBalance() + ord.qty*(-1);
                    break;
                case "open":
                    baseBalance = getBaseBalance();
                    break;
                case "partial-filled":
                    baseBalance = getBaseBalance() + ord.filledQty*(-1);
                    break;
            }
        } else System.out.println("Please check your inputs again");
    }

    public void printBalancesInfo(){
        System.out.println("Quote Balance: " + quoteBalance);
        System.out.println("Available Balance Of Quote CCY: " + availBalanceOfQuoteCCY);
        System.out.println("Base Balance: " + baseBalance);
        System.out.println("Available Balance Of Base CCY: " + availBalanceOfBaseCCY);
        System.out.println("===========================");
    }
}
