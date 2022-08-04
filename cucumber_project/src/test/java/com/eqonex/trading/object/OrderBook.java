package com.eqonex.trading.object;

public class OrderBook {
    Integer pairId;
    Double bestBidPrx;
    Double bestBidQty;
    Double bestAskPrx;
    Double bestAskQty;
    Double markPrx;

    public OrderBook (Integer pairId, Double markPrx, Double bestBidQty, Double bestBidPrx, Double bestAskPrx, Double bestAskQty){
        this.pairId = pairId;
        this.markPrx = markPrx;
        this.bestBidQty = bestBidQty;
        this.bestBidPrx = bestBidPrx;
        this.bestAskPrx = bestAskPrx;
        this.bestAskQty = bestAskQty;
    }

    public Double getBestAskPrx() {
        return bestAskPrx;
    }

    public Double getBestAskQty() {
        return bestAskQty;
    }

    public Double getBestBidQty() {
        return bestBidQty;
    }

    public Double getBestBidPrx() {
        return bestBidPrx;
    }

    public Double getMarkPrx() {
        return markPrx;
    }
}
