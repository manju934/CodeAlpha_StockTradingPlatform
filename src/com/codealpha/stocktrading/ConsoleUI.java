package com.codealpha.stocktrading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ConsoleUI {
    private final StockManager sm;
    private final TradingManager tm;
    private final MarketSimulator simulator;
    private final Scanner scanner;

    public ConsoleUI(StockManager sm, TradingManager tm) {
        this.sm = sm;
        this.tm = tm;
        this.simulator = new MarketSimulator();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean active = true;
        while (active) {
            printMainMenu();
            int choice = InputValidator.readInt(scanner, "Enter selection (1-8): ", 1, 8);
            switch (choice) {
                case 1: manageStocks(); break;
                case 2: manageInvestors(); break;
                case 3: tradeStocks(); break;
                case 4: manageSimulations(); break;
                case 5: displayReports(); break;
                case 6: manageSorting(); break;
                case 7: saveSystemData(); break;
                case 8: exitApplication(); active = false; break;
            }
        }
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("\n==================================================");
        System.out.println("         STOCK TRADING PLATFORM SYSTEM            ");
        System.out.println("==================================================");
        System.out.println("1. Stock Management");
        System.out.println("2. Investor Profile Management");
        System.out.println("3. Trading Actions (Buy/Sell)");
        System.out.println("4. Market Simulation Operations");
        System.out.println("5. Business Metrics & Reports");
        System.out.println("6. Order & Sort Workspace");
        System.out.println("7. Save Session State");
        System.out.println("8. Save & Terminate Session");
        System.out.println("==================================================");
    }

    private void manageStocks() {
        System.out.println("\n--- Stock Management Workspace ---");
        System.out.println("1. Add Stock Entry");
        System.out.println("2. Display Registered Stocks");
        System.out.println("3. Search for Specific Stock");
        System.out.println("4. Edit Stock Value");
        System.out.println("5. Remove Stock Entry");
        System.out.println("6. Back");
        int choice = InputValidator.readInt(scanner, "Selection: ", 1, 6);

        switch (choice) {
            case 1:
                String symbol = InputValidator.readSymbol(scanner, "Symbol (1-5 Chars): ");
                String name = InputValidator.readString(scanner, "Company/Asset Name: ");
                double price = InputValidator.readDouble(scanner, "Opening Market Price ($): ", 0.01, 100000.0);
                if (sm.addStock(new Stock(symbol, name, price))) {
                    System.out.println("Success: Asset catalogued inside inventory.");
                } else {
                    System.out.println("Error: Stock symbol already exists.");
                }
                break;
            case 2:
                displayStockTable(sm.getStocks());
                break;
            case 3:
                String sSymbol = InputValidator.readSymbol(scanner, "Target Search Symbol: ");
                Stock target = sm.findStock(sSymbol);
                if (target != null) {
                    System.out.println(target);
                } else {
                    System.out.println("Error: Specified symbol not found.");
                }
                break;
            case 4:
                String uSymbol = InputValidator.readSymbol(scanner, "Target Symbol for Adjustment: ");
                double uPrice = InputValidator.readDouble(scanner, "New Price Specification ($): ", 0.01, 100000.0);
                if (sm.updateStockPrice(uSymbol, uPrice)) {
                    System.out.println("Success: Stock price adjusted.");
                } else {
                    System.out.println("Error: Stock could not be located.");
                }
                break;
            case 5:
                String dSymbol = InputValidator.readSymbol(scanner, "Target Symbol to Drop: ");
                if (sm.deleteStock(dSymbol)) {
                    System.out.println("Success: Asset reference metadata dropped.");
                } else {
                    System.out.println("Error: Specified asset was not found.");
                }
                break;
        }
    }

    private void manageInvestors() {
        System.out.println("\n--- Investor Registry Menu ---");
        System.out.println("1. Open Investor Account");
        System.out.println("2. Display Registered Accounts");
        System.out.println("3. Manage Account Holdings");
        System.out.println("4. Back");
        int choice = InputValidator.readInt(scanner, "Selection: ", 1, 4);

        switch (choice) {
            case 1:
                String id = "INV-" + (tm.getInvestors().size() + 101);
                String name = InputValidator.readString(scanner, "Full Account Name: ");
                double balance = InputValidator.readDouble(scanner, "Initial Depository Balance ($): ", 0.0, 10000000.0);
                if (tm.addInvestor(new Investor(id, name, balance))) {
                    System.out.println("Success: Account created with ID: " + id);
                } else {
                    System.out.println("Error: System failed to register unique allocation indices.");
                }
                break;
            case 2:
                displayInvestorTable(tm.getInvestors());
                break;
            case 3:
                String tId = InputValidator.readString(scanner, "Enter Target Investor ID: ");
                Investor target = tm.findInvestor(tId);
                if (target != null) {
                    displayPortfolioSummary(target);
                } else {
                    System.out.println("Error: Specified client reference index was not found.");
                }
                break;
        }
    }

    private void tradeStocks() {
        System.out.println("\n--- Live Exchange Desk ---");
        System.out.println("1. Buy Shares Order");
        System.out.println("2. Sell Shares Order");
        System.out.println("3. Display Transaction History Log");
        System.out.println("4. Back");
        int choice = InputValidator.readInt(scanner, "Selection: ", 1, 4);

        if (choice == 3) {
            displayTransactionLogs(tm.getTransactions());
            return;
        } else if (choice == 4) {
            return;
        }

        String id = InputValidator.readString(scanner, "Buyer/Seller Account ID: ");
        Investor investor = tm.findInvestor(id);
        if (investor == null) {
            System.out.println("Error: Unrecognized account designation parameters.");
            return;
        }

        String symbol = InputValidator.readSymbol(scanner, "Asset Trading Symbol: ");
        Stock stock = sm.findStock(symbol);
        if (stock == null) {
            System.out.println("Error: Target asset reference code invalid.");
            return;
        }

        int shares = InputValidator.readInt(scanner, "Volume of Shares: ", 1, 1000000);

        if (choice == 1) {
            if (tm.processBuy(investor, stock, shares)) {
                System.out.println("Success: Purchase executed successfully.");
            } else {
                System.out.println("Error: Order rejected. Check account balances.");
            }
        } else {
            if (tm.processSell(investor, stock, shares)) {
                System.out.println("Success: Sale executed successfully.");
            } else {
                System.out.println("Error: Order rejected. Check holdings quantities.");
            }
        }
    }

    private void manageSimulations() {
        System.out.println("\n--- Market Simulation Tool ---");
        System.out.println("1. Fluctuate Active Market Prices (Simulate Day)");
        System.out.println("2. Back");
        int choice = InputValidator.readInt(scanner, "Selection: ", 1, 2);

        if (choice == 1) {
            simulator.simulateMarket(sm.getStocks());
            System.out.println("System: Active asset values updated.");
            displayStockTable(sm.getStocks());
        }
    }

    private void displayReports() {
        System.out.println("\n--- Reporting Engine Menu ---");
        System.out.println("1. Top Performing Asset (Highest Return %)");
        System.out.println("2. Most Traded Active Stock Volume");
        System.out.println("3. Overall Investors Portfolio Valuation Summary");
        System.out.println("4. Back");
        int choice = InputValidator.readInt(scanner, "Selection: ", 1, 4);

        switch (choice) {
            case 1:
                ArrayList<Stock> perfList = new ArrayList<>(sm.getStocks());
                if (perfList.isEmpty()) {
                    System.out.println("Notice: Stock catalog contains no records.");
                } else {
                    perfList.sort((s1, s2) -> Double.compare(s2.getPerformancePercent(), s1.getPerformancePercent()));
                    System.out.println("\n--- Top Asset Performance Rank ---");
                    System.out.println(perfList.get(0));
                }
                break;
            case 2:
                ArrayList<Stock> tradeList = new ArrayList<>(sm.getStocks());
                if (tradeList.isEmpty()) {
                    System.out.println("Notice: Stock catalog contains no records.");
                } else {
                    tradeList.sort((s1, s2) -> Integer.compare(s2.getVolumeTraded(), s1.getVolumeTraded()));
                    System.out.println("\n--- High Frequency Volume Asset Rank ---");
                    System.out.println(tradeList.get(0));
                }
                break;
            case 3:
                System.out.println("\n--- Aggregate Consolidated Investors Evaluation Report ---");
                for (Investor inv : tm.getInvestors()) {
                    displayPortfolioSummary(inv);
                }
                break;
        }
    }

    private void manageSorting() {
        System.out.println("\n--- Sorting Operations ---");
        System.out.println("1. Sort Stocks by Name");
        System.out.println("2. Sort Stocks by Price");
        System.out.println("3. Sort Investors Alphabetically");
        System.out.println("4. Sort Transactions Chronologically");
        System.out.println("5. Back");
        int choice = InputValidator.readInt(scanner, "Selection: ", 1, 5);

        switch (choice) {
            case 1:
                ArrayList<Stock> nameSort = new ArrayList<>(sm.getStocks());
                nameSort.sort(new Comparators.StockByNameComparator());
                displayStockTable(nameSort);
                break;
            case 2:
                ArrayList<Stock> priceSort = new ArrayList<>(sm.getStocks());
                priceSort.sort(new Comparators.StockByPriceComparator());
                displayStockTable(priceSort);
                break;
            case 3:
                ArrayList<Investor> invSort = new ArrayList<>(tm.getInvestors());
                invSort.sort(new Comparators.InvestorByNameComparator());
                displayInvestorTable(invSort);
                break;
            case 4:
                ArrayList<Transaction> txSort = new ArrayList<>(tm.getTransactions());
                txSort.sort(new Comparators.TransactionByDateComparator());
                displayTransactionLogs(txSort);
                break;
        }
    }

    private void saveSystemData() {
        FileManager.saveStocks(sm.getStocks());
        FileManager.saveInvestors(tm.getInvestors());
        FileManager.saveTransactions(tm.getTransactions());
        System.out.println("System: Storage synchronization completed successfully.");
    }

    private void loadData() {
        sm.setStocks(FileManager.loadStocks());
        tm.setInvestors(FileManager.loadInvestors());
        tm.setTransactions(FileManager.loadTransactions());
        System.out.println("System: Storage files loaded into session registry.");
    }

    private void exitApplication() {
        saveSystemData();
        System.out.println("Exiting Stock Trading Platform. Terminating session.");
    }

    private void displayStockTable(ArrayList<Stock> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("\n+-------------------------------------------------------------+");
            System.out.println("|               Stock Registry Is Currently Empty            |");
            System.out.println("+-------------------------------------------------------------+");
            return;
        }
        System.out.println("\n+--------+------------------------+------------+------------+---------------+");
        System.out.println("| Symbol | Name                   | Price ($)  | Vol Traded | Performance % |");
        System.out.println("+--------+------------------------+------------+------------+---------------+");
        for (Stock s : list) {
            System.out.printf("| %-6s | %-22s | %-10.2f | %-10d | %-+13.2f%% |%n",
                    s.getSymbol(), s.getName(), s.getCurrentPrice(), s.getVolumeTraded(), s.getPerformancePercent());
        }
        System.out.println("+--------+------------------------+------------+------------+---------------+");
    }

    private void displayInvestorTable(ArrayList<Investor> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("\n+--------------------------------------------------------+");
            System.out.println("|               Investor Registry Is Empty               |");
            System.out.println("+--------------------------------------------------------+");
            return;
        }
        System.out.println("\n+------------+------------------------+------------------+");
        System.out.println("| ID         | Full Name              | Cash Balance ($) |");
        System.out.println("+------------+------------------------+------------------+");
        for (Investor i : list) {
            System.out.printf("| %-10s | %-22s | %-16.2f |%n",
                    i.getId(), i.getName(), i.getBalance());
        }
        System.out.println("+------------+------------------------+------------------+");
    }

    private void displayPortfolioSummary(Investor investor) {
        System.out.println("\n==================================================");
        System.out.printf("INVESTOR PORTFOLIO SUMMARY: %s (%s)%n", investor.getName(), investor.getId());
        System.out.println("==================================================");
        System.out.printf("Available cash Capital Balance: $%.2f%n", investor.getBalance());
        System.out.println("--------------------------------------------------");
        System.out.println("CURRENT VALUED ASSETS:");
        System.out.println("+--------+-------------+------------------+---------------+");
        System.out.println("| Symbol | Shares Held | Unit Value ($)   | Value ($)     |");
        System.out.println("+--------+-------------+------------------+---------------+");
        double totalAssetValue = 0.0;
        for (var entry : investor.getPortfolio().getHoldings().entrySet()) {
            String symbol = entry.getKey();
            int shares = entry.getValue();
            Stock stock = sm.findStock(symbol);
            double currentPrice = stock != null ? stock.getCurrentPrice() : 0.0;
            double assetValue = shares * currentPrice;
            totalAssetValue += assetValue;
            System.out.printf("| %-6s | %-11d | %-16.2f | %-13.2f |%n",
                    symbol, shares, currentPrice, assetValue);
        }
        System.out.println("+--------+-------------+------------------+---------------+");
        System.out.printf("Total Stocks Portfolio Value : $%.2f%n", totalAssetValue);
        System.out.printf("Consolidated Total Net Worth : $%.2f%n", investor.getBalance() + totalAssetValue);
        System.out.println("==================================================");
    }

    private void displayTransactionLogs(ArrayList<Transaction> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("\n+---------------------------------------------------------------------------------+");
            System.out.println("|                       Exchange Registry Contains No Logs                        |");
            System.out.println("+---------------------------------------------------------------------------------+");
            return;
        }
        System.out.println("\n+---------------+------------+--------+------+--------+--------------+---------------------+");
        System.out.println("| TxID          | InvID      | Symbol | Type | Shares | Unit Price   | Time Recorded       |");
        System.out.println("+---------------+------------+--------+------+--------+--------------+---------------------+");
        for (Transaction t : list) {
            System.out.printf("| %-13s | %-10s | %-6s | %-4s | %-6d | %-12.2f | %-19s |%n",
                    t.getId(), t.getInvestorId(), t.getStockSymbol(), t.getType(), t.getShares(), t.getPricePerShare(),
                    t.getTimestamp().toString().substring(0, 19).replace('T', ' '));
        }
        System.out.println("+---------------+------------+--------+------+--------+--------------+---------------------+");
    }
}