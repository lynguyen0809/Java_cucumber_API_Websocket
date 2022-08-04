package sessions.s04.oop;

import static sessions.s04.oop.User.truncate;

public class SpotOrder {
    double prx;
    double qty;
    String side;
    String ordStt;
    double filledQty;
    double filledPrx;

    public SpotOrder(String side, double prx, double qty){
        this.side = side;
        this.prx = prx;
        this.qty = qty;
    }

    public double getOrdNotional(){
        return truncate(qty * prx,6);
    }
}
