package com.codealpha.stocktrading;

import java.util.Comparator;

public class Comparators {
    private Comparators() {}
    public static class StockByNameComparator implements Comparator<Stock> {
        @Override
        public int compare(Stock s1, Stock s2) {
            if (s1 == null || s2 == null) return 0;
            return s1.getName().compareToIgnoreCase(s2.getName());
        }
    }
    public static class StockByPriceComparator implements Comparator<Stock> {
        @Override
        public int compare(Stock s1, Stock s2) {
            if (s1 == null || s2 == null) return 0;
            return Double.compare(s1.getCurrentPrice(), s2.getCurrentPrice());
        }
    }
    public static class InvestorByNameComparator implements Comparator<Investor> {
        @Override
        public int compare(Investor i1, Investor i2) {
            if (i1 == null || i2 == null) return 0;
            return i1.getName().compareToIgnoreCase(i2.getName());
        }
    }
    public static class TransactionByDateComparator implements Comparator<Transaction> {
        @Override
        public int compare(Transaction t1, Transaction t2) {
            if (t1 == null || t2 == null) return 0;
            return t1.getTimestamp().compareTo(t2.getTimestamp());
        }
    }
}