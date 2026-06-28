package com.codealpha.stocktrading;

import java.util.ArrayList;

public class TradingManager {
    private ArrayList<Investor> investors;
    private ArrayList<Transaction> transactions;

    public TradingManager() {
        this.investors = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public ArrayList<Investor> getInvestors() { return investors; }
    public void setInvestors(ArrayList<Investor> investors) { this.investors = investors != null ? investors : new ArrayList<>(); }
    public ArrayList<Transaction> getTransactions() { return transactions; }
    public void setTransactions(ArrayList<Transaction> transactions) { this.transactions = transactions != null ? transactions : new ArrayList<>(); }

    public boolean addInvestor(Investor investor) {
        if (investor == null || findInvestor(investor.getId()) != null) return false;
        investors.add(investor);
        return true;
    }

    public Investor findInvestor(String id) {
        if (id == null) return null;
        for (Investor i : investors) {
            if (i.getId().equalsIgnoreCase(id)) return i;
        }
        return null;
    }

    public boolean processBuy(Investor investor, Stock stock, int shares) {
        if (investor == null || stock == null || shares <= 0) return false;
        double cost = stock.getCurrentPrice() * shares;
        if (investor.getBalance() < cost) return false;

        investor.setBalance(investor.getBalance() - cost);
        investor.getPortfolio().addShares(stock.getSymbol(), shares);
        stock.setVolumeTraded(stock.getVolumeTraded() + shares);

        String txId = "TX-B-" + (transactions.size() + 1001);
        transactions.add(new Transaction(txId, investor.getId(), stock.getSymbol(), "BUY", shares, stock.getCurrentPrice()));
        return true;
    }

    public boolean processSell(Investor investor, Stock stock, int shares) {
        if (investor == null || stock == null || shares <= 0) return false;
        if (investor.getPortfolio().getShares(stock.getSymbol()) < shares) return false;

        double credit = stock.getCurrentPrice() * shares;
        investor.setBalance(investor.getBalance() + credit);
        investor.getPortfolio().removeShares(stock.getSymbol(), shares);
        stock.setVolumeTraded(stock.getVolumeTraded() + shares);

        String txId = "TX-S-" + (transactions.size() + 1001);
        transactions.add(new Transaction(txId, investor.getId(), stock.getSymbol(), "SELL", shares, stock.getCurrentPrice()));
        return true;
    }
}