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
import java.util.Comparator;

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
                    try (FileWriter writer = new FileWriter("transactions.csv", true)) {
                        writer.write(csvLine + System.lineSeparator());
                    }
                    transactions.add(deposit);
                    System.out.println("Deposit saved successfully!");

                    System.out.println("Created deposit: " + deposit.getDescription());
                    System.out.println("Amount: " + amount);
                    System.out.println("Description: " + description);
                    System.out.println("vendor: " + vendor);
                    break;

                case "P": {
                    System.out.print("Enter payment description: ");
                    String paymentDescription = scanner.nextLine();

                    System.out.print("Enter vendor: ");
                    String paymentVendor = scanner.nextLine();

                    System.out.print("Enter payment amount: ");
                    String paymentAmountText = scanner.nextLine();

                    double paymentAmount = Double.parseDouble(paymentAmountText);
                    paymentAmount = -Math.abs(paymentAmount);
                    Transaction payment = new Transaction(
                            LocalDate.now(),
                            LocalTime.now(),
                            paymentDescription,
                            paymentVendor,
                            paymentAmount
                    );
                    String paymentCsvLine = payment.getDate() + "|" +
                            payment.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "|" +
                            payment.getDescription() + "|" +
                            payment.getVendor() + "|" +
                            String.format(Locale.US, "%.2f", payment.getAmount());
                    System.out.println("CSV location: " +
                            new java.io.File("transactions.csv").getAbsolutePath());
                    try (FileWriter writer = new FileWriter("transactions.csv", true)) {
                        writer.write(paymentCsvLine + System.lineSeparator());

                        transactions.add(payment);

                        System.out.println("payment saved successfully!");

                    } catch (IOException e) {
                        System.out.println("error saving payment: " + e.getMessage());

                    }

                    System.out.println("payment amount: " + paymentAmount);

                    System.out.println("Description: " + paymentDescription);
                    System.out.println("vendor: " + paymentVendor);

                    System.out.println("Payment: " + payment.getDescription());
                    System.out.println("Amount: " + payment.getAmount());
                    break;
                }

                case "L": {
                    transactions.sort(Comparator.comparing(Transaction::getDate)
                            .thenComparing(Transaction::getTime)
                            .reversed());

                    while (true) {
                        System.out.println("Ledger selected");
                        System.out.println("A) All Transactions");
                        System.out.println("D) Deposits");
                        System.out.println("P) Payments");
                        System.out.println("R) Reports");
                        System.out.println("H) Home");

                        System.out.print("Enter your choice: ");
                        String ledgerChoice = scanner.nextLine();

                        if (ledgerChoice.equalsIgnoreCase("H")) {
                            break;
                        }

                        switch (ledgerChoice.toUpperCase()) {
                            case "A":
                                System.out.println("All Transactions");

                                for (Transaction t : transactions) {
                                    System.out.println(
                                            t.getDate() + " | " +
                                            t.getTime() + " | " +
                                            t.getDescription() + " | " +
                                            t.getVendor() + " | " +
                                            t.getAmount()
                                    );
                                }
                                break;
                            case "D":
                                System.out.println("Deposists: ");

                                for (Transaction t : transactions) {
                                    if (t.getAmount() > 0) {
                                        System.out.println(
                                        t.getDate() + " | " +
                                        t.getTime() + " | " +
                                        t.getDescription() + " | " +
                                        t.getVendor() + " | " +
                                        t.getAmount()
                                        );

                                    }
                                }
                            case "P":
                                System.out.println("Payments: ");

                                for (Transaction t : transactions) {
                                    if(t.getAmount() < 0) {
                                        System.out.println(
                                                t.getDate() + " | " +
                                                        t.getTime() + " | " +
                                                        t.getDescription() + " | " +
                                                        t.getVendor() + " | " +
                                                        t.getAmount()
                                        );
                                    }
                                }
                                break;
                        }
                    }
                    break;
                }



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

