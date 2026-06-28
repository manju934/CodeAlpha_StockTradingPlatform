package com.codealpha.stocktrading;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing CodeAlpha Stock Trading Engine...");

        StockManager sm = new StockManager();
        TradingManager tm = new TradingManager();

        ArrayList<Stock> activeStocks = FileManager.loadStocks();
        if (!activeStocks.isEmpty()) {
            sm.setStocks(activeStocks);
            System.out.println("Restored " + activeStocks.size() + " stock reference catalogs.");
        } else {
            sm.addStock(new Stock("AAPL", "Apple Inc.", 175.50));
            sm.addStock(new Stock("GOOGL", "Alphabet Inc.", 142.20));
            sm.addStock(new Stock("MSFT", "Microsoft Corp.", 415.10));
            sm.addStock(new Stock("AMZN", "Amazon.com Inc.", 178.30));
            sm.addStock(new Stock("TSLA", "Tesla Inc.", 170.80));
            System.out.println("Initialized benchmark reference indices successfully.");
        }

        tm.setInvestors(FileManager.loadInvestors());
        tm.setTransactions(FileManager.loadTransactions());

        ConsoleUI ui = new ConsoleUI(sm, tm);
        ui.run();
    }
}