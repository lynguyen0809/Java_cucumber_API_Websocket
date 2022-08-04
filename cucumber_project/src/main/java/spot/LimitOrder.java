package spot;

import static spot.UtilFunctions.truncate;

public class LimitOrder extends SpotOrder{

    public LimitOrder(String side, double prx, double qty){
        this.side = side;
        this.prx = prx;
        this.qty = qty;
    }

    @Override
    public double getOrdNotional(){
        return truncate(qty * prx,6);
    }

    @Override
    public double getFilledOrdNotional(double filledQty, double filledPrx) {
        return truncate(filledQty * filledPrx,6);
    }

    @Override
    public double getOrderFees(double filledPrx, double filledQty, double userFeeRate ) {
        return truncate(filledQty * filledPrx * userFeeRate,6);
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
        return isQtyValid() && isPrxValid(prx);
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
}
