package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import java.io.FileWriter;

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
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();

                    System.out.print("Enter vendor: ");
                    String vendor = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    String amountText = scanner.nextLine();
                    double amount = Double.parseDouble(amountText);
                    Transaction deposit = new Transaction(
                            LocalDate.now(),
                            LocalTime.now(),
                            description,
                            vendor,
                            amount
                    );
                    String csvLine = deposit.getDate() + "|" +
                            deposit.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "|" +
                            deposit.getDescription() + "|" +
                            deposit.getVendor() + "|" +
                            String.format(Locale.US, "%.2f", deposit.getAmount());
                    System.out.println(csvLine);
                    try(FileWriter writer = new FileWriter("transactions.csv", true)) {
                        writer.write(csvLine + System.lineSeparator());
                    }
                        transactions.add(deposit);
                        System.out.println("Deposit saved successfully!");

                    System.out.println("Created deposit: " + deposit.getDescription());
                    System.out.println("Amount: " + amount);
                    System.out.println("Description: " + description);
                    System.out.println("vendor: " + vendor);
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

