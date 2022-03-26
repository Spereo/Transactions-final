package com.example.course.controllers;

import com.example.course.Parser;
import com.example.course.models.Transaction;
import com.example.course.repos.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class MainController {
    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/")
    public String home(Model model) throws IOException {
        return "home";
    }

    @GetMapping("/transactions")
    public String transactions(Model model) throws IOException {
        ArrayList<Transaction> transactions = Parser.ParseTransactionCSV("./DataCSV/transactions_cut.csv");
        Iterable<Transaction> transactions1 = transactionRepo.findAll();
        model.addAttribute("transactions", transactions);
        model.addAttribute("transactions1", transactions1);
        return "transactions";
    }

    @PostMapping("/check")
    public String check(@RequestParam String action) {
        if(action.equals("CSVtoDB")) {
            return "CSVtoDB";
        }
        if(action.equals("median")) {
            return "transactions";
        }
        if(action.equals("searchNotEmpty")) {
            return "home";
        }
        if(action.equals("medianSpecial"))
        {
            return "transactions";
        } else {
            return "home";
        }
    }

    @PostMapping("/ensureCSVtoDB")
    public String ensureCSVtoDB(@RequestParam String action) {
        if(action.equals("confirm")) {
            return "home";
        } else {
            return "transactions";
        }
    }
}
