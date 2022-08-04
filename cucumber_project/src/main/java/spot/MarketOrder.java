package spot;

import static spot.UtilFunctions.truncate;

public class MarketOrder extends SpotOrder{
    double ordFee;


    public MarketOrder(String side, double qty, double markPrx){
        this.side = side;
        this.qty = qty;
        this.markPrx = markPrx;
    }

    @Override
    public double getOrdNotional(){
        return truncate(qty * markPrx,6);
    }

    public double getFilledOrdNotional(double filledPrx, double filledQty) { return truncate(filledQty * filledPrx,6);}

    @Override
    public double getOrderFees(double filledPrx, double filledQty,double userFeeRate) {
        return ordFee = truncate(filledPrx * filledQty * userFeeRate,6);
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
    public String getOrdStt() {
        return this.ordStt;
    }


    @Override
    public boolean isOrderInfoValid(){
        return isQtyValid() && isPrxValid(markPrx);
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

    public boolean isPrxValid(double qty){
        if(qty == 0){
            System.out.println("Please input Price");
            return false;
        }
        else if(qty < 0){
            System.out.println("Price is incorrect, should be greater than 0");
            return false;
        }
        else {
            System.out.println("Valid Price");
            return true;
        }
    }

    @Override
    public double getReservedBalanceforBuy(double ordNotional, double ordFee){
        return ordNotional + ordFee;
    }

    @Override
    public double getReservedBalanceforSell(double qty){
        return qty;
    }
}
