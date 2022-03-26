package com.example.course;

import com.example.course.models.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static ArrayList<Transaction> ParseTransactionCSV(String filePath) throws IOException {
        ArrayList<Transaction> transactions = new ArrayList<>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath));

        int counter = 0;
        for(String fileLine : fileLines) {
            String[] splitedText = fileLine.split(",");
            List<String> columnList = new ArrayList<>();
            for(int i = 0; i < splitedText.length; i++) {
                columnList.add(splitedText[i]);
            }
            if(counter != 0)
            {
                Transaction transaction = new Transaction();
                transaction.setCustomer_id(Integer.parseInt(columnList.get(0)));
                transaction.setTr_datetime(columnList.get(1));
                transaction.setMcc_code(Integer.parseInt(columnList.get(2)));
                transaction.setTr_type(Integer.parseInt(columnList.get(3)));
                transaction.setAmount(Double.parseDouble(columnList.get(4)));
                if(columnList.size() == 6)
                    transaction.setTerm_id(columnList.get(5));
                else
                    transaction.setTerm_id("NULL");
                transactions.add(transaction);
            }
            counter++;
        }
        return transactions;
    }
}
