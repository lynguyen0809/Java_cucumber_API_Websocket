package spot;

import java.util.Locale;

public abstract class SpotOrder {
    public double markPrx;
    public double prx;
    public double qty;
    public String ordStt;
    public double filledPrx;
    public double filledQty;
    public String side;

    public abstract double getOrdNotional();

    public abstract double getFilledOrdNotional(double filledQty, double filledPrx);

    public abstract double getOrderFees(double FilledPrx, double FilledQty, double userFeeRate);

    public abstract boolean isOrderInfoValid();

    public abstract double getReservedBalanceforBuy(double ordNotional, double ordFee);

    public abstract double getReservedBalanceforSell(double qty);

    public abstract String getOrdStt();

    public abstract double getFilledPrx();

    public abstract double getFilledQty();

    public Integer getOrdSide(){
        side = side.toLowerCase();
        if (side.equals("1") || side.equals("buy")){
            return 1;
        } else if (side.equals("2") || side.equals("sell")){
            return 2;
        } else {
            return 3;
        }
    };

}
