package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
        String line;
        ArrayList<Transaction> transactions = new ArrayList<>();
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            double amount = Double.parseDouble(parts[4]);
            Transaction t = new Transaction(LocalDate.parse(parts[0]), LocalTime.parse(parts[1]),parts[2],parts[3],amount);
            transactions.add(t);
        }
        System.out.println(transactions.size());



    }



}
