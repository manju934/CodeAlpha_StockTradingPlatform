package com.codealpha.stocktrading;

import java.util.ArrayList;
import java.util.Random;

public class MarketSimulator {
    private final Random random;

    public MarketSimulator() { this.random = new Random(); }

    public void simulateMarket(ArrayList<Stock> stocks) {
        if (stocks == null || stocks.isEmpty()) return;
        for (Stock stock : stocks) {
            double changePercent = (random.nextDouble() * 20.0) - 10.0;
            double oldPrice = stock.getCurrentPrice();
            double newPrice = oldPrice * (1.0 + (changePercent / 100.0));
            if (newPrice < 0.01) newPrice = 0.01;
            stock.setCurrentPrice(Math.round(newPrice * 100.0) / 100.0);
        }
    }
}