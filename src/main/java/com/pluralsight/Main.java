package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
        String line;
        ArrayList<Transaction> transactions = new ArrayList<>();
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            double amount = Double.parseDouble(parts[4]);
            Transaction t = new Transaction(LocalDate.parse(parts[0]), LocalTime.parse(parts[1]), parts[2], parts[3], amount);
            transactions.add(t);
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Home screen");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "D":
                    System.out.println("Add Deposit selected");
                    break;

                case "P":
                    System.out.println("Make Payment selected");
                    break;

                case "L":
                    System.out.println("Ledger selected");
                    break;

                case "X":
                    System.out.println("Goodbye");
                    return;

                default:
                    System.out.println("Invalid choice! Try again");
                    break;



            }

        }
    }
}

