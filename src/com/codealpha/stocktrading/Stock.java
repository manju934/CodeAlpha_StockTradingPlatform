package com.codealpha.stocktrading;

import java.io.Serializable;
import java.util.Objects;

public class Stock implements Serializable, Comparable<Stock> {
    private static final long serialVersionUID = 1L;
    private String symbol;
    private String name;
    private double initialPrice;
    private double currentPrice;
    private int volumeTraded;

    public Stock(String symbol, String name, double currentPrice) {
        this.symbol = symbol.toUpperCase();
        this.name = name;
        this.initialPrice = currentPrice;
        this.currentPrice = currentPrice;
        this.volumeTraded = 0;
    }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol.toUpperCase(); }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getInitialPrice() { return initialPrice; }
    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
    public int getVolumeTraded() { return volumeTraded; }
    public void setVolumeTraded(int volumeTraded) { this.volumeTraded = volumeTraded; }

    public double getPerformancePercent() {
        if (initialPrice <= 0) return 0.0;
        return ((currentPrice - initialPrice) / initialPrice) * 100.0;
    }

    @Override
    public int compareTo(Stock other) {
        if (other == null) return 1;
        return this.symbol.compareToIgnoreCase(other.symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() { return Objects.hash(symbol); }

    @Override
    public String toString() {
        return String.format("Stock: %-5s | %-15s | Price: $%-8.2f | Vol: %-6d | Performance: %+.2f%%",
                symbol, name, currentPrice, volumeTraded, getPerformancePercent());
    }
}