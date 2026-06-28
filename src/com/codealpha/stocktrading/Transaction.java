package com.codealpha.stocktrading;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String investorId;
    private String stockSymbol;
    private String type;
    private int shares;
    private double pricePerShare;
    private LocalDateTime timestamp;

    public Transaction(String id, String investorId, String stockSymbol, String type, int shares, double pricePerShare) {
        this.id = id;
        this.investorId = investorId;
        this.stockSymbol = stockSymbol.toUpperCase();
        this.type = type.toUpperCase();
        this.shares = shares;
        this.pricePerShare = pricePerShare;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getInvestorId() { return investorId; }
    public String getStockSymbol() { return stockSymbol; }
    public String getType() { return type; }
    public int getShares() { return shares; }
    public double getPricePerShare() { return pricePerShare; }
    public LocalDateTime getTimestamp() { return timestamp; }
}