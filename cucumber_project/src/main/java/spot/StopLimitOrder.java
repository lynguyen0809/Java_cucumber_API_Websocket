package spot;

import static spot.UtilFunctions.truncate;

public class StopLimitOrder extends SpotOrder{
    double stopPrx;
    double lastTradePrx;
    double ordFee;
    double ordNotional;

    public StopLimitOrder(String side, double qty, double prx, double stopPrx, double lastTradePrx){
        this.side = side;
        this.stopPrx = stopPrx;
        this.prx = prx;
        this.qty = qty;
        this.lastTradePrx = lastTradePrx;
    }

    @Override
    public double getOrdNotional(){
        return ordNotional = truncate(qty * prx,6);
    }

    @Override
    public double getFilledOrdNotional(double filledQty, double filledPrx) {
        return truncate(filledQty * filledPrx,6);
    }

    @Override
    public double getOrderFees(double filledPrx, double filledQty,double userFeeRate ) {
        return truncate(filledPrx * filledQty * userFeeRate,6);
    }

    @Override
    public String getOrdStt() {
        return this.ordStt;
    }

    @Override
    public double getFilledQty() {
        return this.filledQty;
    }

    @Override
    public double getFilledPrx() {
        return this.filledPrx;
    }

    @Override
    public double getReservedBalanceforBuy(double ordNotional, double ordFee){
        return ordNotional + ordFee;
    }

    @Override
    public double getReservedBalanceforSell(double qty){
        return qty;
    }

    @Override
    public boolean isOrderInfoValid(){
        return isQtyValid() && isPrxValid(prx) && isStopPrxValid();
    }

    public boolean isQtyValid(){
        if(qty == 0){
            System.out.println("Please input qty");
            return false;
        }
        else if(qty < 0){
            System.out.println("Order qty is incorrect, should be greater than 0");
            return false;
        }
        else {
            System.out.println("Valid Order Qty");
            return true;
        }
    }

    public boolean isPrxValid(double prx){
        if(prx == 0){
            System.out.println("Please input Price");
            return false;
        }
        else if(prx < 0){
            System.out.println("Price is incorrect, should be greater than 0");
            return false;
        }
        else {
            System.out.println("Valid Price");
            return true;
        }
    }

    public boolean isStopPrxValid(){
        if(stopPrx == 0 || stopPrx < 0){
            System.out.println("Please input StopPrx, should be greater than 0");
            return false;
        }
        else if(stopPrx < lastTradePrx){
            if (side.equals("sell")) {
                System.out.println("Valid stop Sell price");
                return false;
            } else if(side.equals("buy")) {
                System.out.println("Invalid stop Buy price");
                return false;
            } else {
                System.out.println("Please input valid Stop Price");
                return false;
            }
        }
        else if(stopPrx > lastTradePrx) {
            if (side.equals("sell")) {
                System.out.println("Invalid stop Sell price");
                return false;
            } else if(side.equals("buy")) {
                System.out.println("Valid stop Buy price");
                return false;
            } else {
                System.out.println("Please input valid Stop Price");
                return false;
            }
        }
        else return true;
    }
}
