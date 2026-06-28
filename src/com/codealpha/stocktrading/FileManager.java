package com.codealpha.stocktrading;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private FileManager() {}
    public static void saveStocks(ArrayList<Stock> stocks) { saveData(Constants.STOCKS_FILE, stocks); }
    @SuppressWarnings("unchecked")
    public static ArrayList<Stock> loadStocks() {
        Object obj = loadData(Constants.STOCKS_FILE);
        return obj instanceof ArrayList ? (ArrayList<Stock>) obj : new ArrayList<>();
    }
    public static void saveInvestors(ArrayList<Investor> investors) { saveData(Constants.INVESTORS_FILE, investors); }
    @SuppressWarnings("unchecked")
    public static ArrayList<Investor> loadInvestors() {
        Object obj = loadData(Constants.INVESTORS_FILE);
        return obj instanceof ArrayList ? (ArrayList<Investor>) obj : new ArrayList<>();
    }
    public static void saveTransactions(ArrayList<Transaction> transactions) { saveData(Constants.TRANSACTIONS_FILE, transactions); }
    @SuppressWarnings("unchecked")
    public static ArrayList<Transaction> loadTransactions() {
        Object obj = loadData(Constants.TRANSACTIONS_FILE);
        return obj instanceof ArrayList ? (ArrayList<Transaction>) obj : new ArrayList<>();
    }
    private static void saveData(String filePath, Object obj) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            System.err.println("Warning: Storage structures could not be initialized.");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Serialization Error tracking file: " + filePath + " - " + e.getMessage());
        }
    }
    private static Object loadData(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}