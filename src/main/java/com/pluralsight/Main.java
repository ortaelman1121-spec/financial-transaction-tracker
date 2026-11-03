package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }



    }


}
