package com.codealpha.stocktrading;

import java.io.Serializable;

public class Investor implements Serializable, Comparable<Investor> {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private double balance;
    private final Portfolio portfolio;

    public Investor(String id, String name, double initialBalance) {
        this.id = id;
        this.name = name;
        this.balance = initialBalance;
        this.portfolio = new Portfolio();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public Portfolio getPortfolio() { return portfolio; }

    @Override
    public int compareTo(Investor other) {
        if (other == null) return 1;
        return this.id.compareToIgnoreCase(other.id);
    }

    @Override
    public String toString() {
        return String.format("ID: %-8s | Name: %-20s | Cash Account: $%-12.2f", id, name, balance);
    }
}