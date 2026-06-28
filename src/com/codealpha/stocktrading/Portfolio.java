package com.codealpha.stocktrading;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Portfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, Integer> holdings;

    public Portfolio() { this.holdings = new HashMap<>(); }
    public Map<String, Integer> getHoldings() { return holdings; }

    public void addShares(String symbol, int shares) {
        if (symbol == null || shares <= 0) return;
        String key = symbol.toUpperCase();
        holdings.put(key, holdings.getOrDefault(key, 0) + shares);
    }

    public boolean removeShares(String symbol, int shares) {
        if (symbol == null || shares <= 0) return false;
        String key = symbol.toUpperCase();
        int currentShares = holdings.getOrDefault(key, 0);
        if (currentShares < shares) return false;
        if (currentShares == shares) holdings.remove(key);
        else holdings.put(key, currentShares - shares);
        return true;
    }

    public int getShares(String symbol) {
        if (symbol == null) return 0;
        return holdings.getOrDefault(symbol.toUpperCase(), 0);
    }
}