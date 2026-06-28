package com.codealpha.stocktrading;

import java.util.Scanner;

public class InputValidator {
    private InputValidator() {}
    public static String readString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("Error: Entry field cannot remain empty.");
        }
    }
    public static String readSymbol(Scanner sc, String prompt) {
        while (true) {
            String val = readString(sc, prompt).toUpperCase();
            if (val.matches("^[A-Z]{1,5}$")) return val;
            System.out.println("Error: Stock symbols must scale between 1 and 5 alphabetic letters.");
        }
    }
    public static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            try {
                int num = Integer.parseInt(val);
                if (num >= min && num <= max) return num;
                System.out.printf("Error: Selection out of range. Input %d - %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please provide valid integers.");
            }
        }
    }
    public static double readDouble(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            try {
                double num = Double.parseDouble(val);
                if (num >= min && num <= max) return num;
                System.out.printf("Error: Minimum and maximum bounds are %.2f and %.2f.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Error: Valid decimal formats are required.");
            }
        }
    }
}