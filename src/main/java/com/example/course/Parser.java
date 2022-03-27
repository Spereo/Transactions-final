package com.example.course;

import com.example.course.models.Gender_train;
import com.example.course.models.Tr_mcc_codes;
import com.example.course.models.Tr_types;
import com.example.course.models.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static ArrayList<Transaction> ParseTransaction(String filePath) throws IOException {
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

    public static ArrayList<Gender_train> ParseGender_train(String filePath) throws IOException {
        ArrayList<Gender_train> gender_trains = new ArrayList<>();
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
                Gender_train gender_train = new Gender_train();
                gender_train.setCustomer_id(Integer.parseInt(columnList.get(0)));
                gender_train.setGender(Integer.parseInt(columnList.get(1)));
                gender_trains.add(gender_train);
            }
            counter++;
        }
        return gender_trains;
    }

    public static ArrayList<Tr_mcc_codes> ParseTr_mcc_codes(String filePath) throws IOException {
        ArrayList<Tr_mcc_codes> tr_mcc_codes = new ArrayList<>();
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
                Tr_mcc_codes tr_mcc_code = new Tr_mcc_codes();
                tr_mcc_code.setMcc_code(Integer.parseInt(columnList.get(0)));
                tr_mcc_code.setMcc_description(columnList.get(1));
                tr_mcc_codes.add(tr_mcc_code);
            }
            counter++;
        }
        return tr_mcc_codes;
    }

    public static ArrayList<Tr_types> ParseTr_types(String filePath) throws IOException {
        ArrayList<Tr_types> tr_types = new ArrayList<>();
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
                Tr_types tr_type = new Tr_types();
                tr_type.setTr_type(Integer.parseInt(columnList.get(0)));
                tr_type.setTr_description(columnList.get(1));
                tr_types.add(tr_type);
            }
            counter++;
        }
        return tr_types;
    }
}
