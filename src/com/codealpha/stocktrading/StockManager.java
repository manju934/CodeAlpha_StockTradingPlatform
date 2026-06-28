package com.codealpha.stocktrading;

import java.util.ArrayList;

public class StockManager {
    private ArrayList<Stock> stocks;

    public StockManager() { this.stocks = new ArrayList<>(); }
    public ArrayList<Stock> getStocks() { return stocks; }
    public void setStocks(ArrayList<Stock> stocks) { this.stocks = stocks != null ? stocks : new ArrayList<>(); }

    public boolean addStock(Stock stock) {
        if (stock == null || findStock(stock.getSymbol()) != null) return false;
        stocks.add(stock);
        return true;
    }

    public Stock findStock(String symbol) {
        if (symbol == null) return null;
        for (Stock s : stocks) {
            if (s.getSymbol().equalsIgnoreCase(symbol)) return s;
        }
        return null;
    }

    public boolean updateStockPrice(String symbol, double newPrice) {
        Stock s = findStock(symbol);
        if (s != null) {
            s.setCurrentPrice(newPrice);
            return true;
        }
        return false;
    }

    public boolean deleteStock(String symbol) {
        Stock s = findStock(symbol);
        if (s != null) {
            stocks.remove(s);
            return true;
        }
        return false;
    }
}