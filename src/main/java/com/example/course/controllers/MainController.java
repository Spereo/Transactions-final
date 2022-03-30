package com.example.course.controllers;

import com.example.course.Parser;
import com.example.course.models.Gender_train;
import com.example.course.models.Tr_mcc_codes;
import com.example.course.models.Tr_types;
import com.example.course.models.Transaction;
import com.example.course.repos.Gender_train_Repo;
import com.example.course.repos.Tr_mcc_codes_Repo;
import com.example.course.repos.Tr_types_Repo;
import com.example.course.repos.TransactionRepo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private Tr_types_Repo tr_types_repo;

    @Autowired
    private Tr_mcc_codes_Repo tr_mcc_codes_repo;

    @Autowired
    private Gender_train_Repo gender_train_repo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String home(Model model) throws IOException {
        return "home";
    }

    @PostMapping("/check")
    public String check(Model model, @RequestParam String action) {
        if(action.equals("CSVtoDB")) {
            File directory = new File(uploadPath);
            List<File> existingFiles = new ArrayList<>();
            boolean isNotEmpty = directory.list().length != 0;
            if(isNotEmpty) {
                for(File file : directory.listFiles()) {
                    if(file.isFile())
                        existingFiles.add(file);
                }
            }
            model.addAttribute("existingFiles", existingFiles);
            model.addAttribute("isNotEmpty", isNotEmpty);
            return "CSVtoDB";
        }
        if(action.equals("median")) {
            return "median";
        }
        if(action.equals("searchNotEmpty")) {
            return "notEmpty";
        }
        if(action.equals("medianSpecial"))
        {
            return "medianUnique";
        } else {
            return "home";
        }
    }

    @PostMapping("/ensureCSVtoDB")
    public String ensureCSVtoDB(Model model, @RequestParam String action, @RequestParam("file") MultipartFile file) throws IOException {
        File directory = new File(uploadPath);
        List<File> existingFiles = new ArrayList<>();
        boolean isNotEmpty = directory.list().length != 0;
        if(isNotEmpty) {
            for(File searchedFile : directory.listFiles()) {
                if(searchedFile.isFile())
                    existingFiles.add(searchedFile);
            }
        }
        model.addAttribute("existingFiles", existingFiles);
        model.addAttribute("isNotEmpty", isNotEmpty);

        if(action.equals("add")) {
            boolean written = false;
            if(file != null) {
                File convFile = new File(uploadPath + file.getOriginalFilename());

                written = convFile.createNewFile();
                if(written) {
                    FileOutputStream fos = new FileOutputStream(convFile);

                    fos.write(file.getBytes());
                    fos.close();
                }
            }
            model.addAttribute("isWritten", written);
        } else if(action.equals("confirm")) {
            ArrayList<Gender_train> gender_trains = Parser.ParseGender_train(uploadPath + "gender_train_cut.csv");
            for(Gender_train gender_train : gender_trains) {
                gender_train_repo.save(gender_train);
            }

            ArrayList<Tr_mcc_codes> tr_mcc_codes = Parser.ParseTr_mcc_codes(uploadPath + "tr_mcc_codes.csv");
            for(Tr_mcc_codes tr_mcc_code : tr_mcc_codes) {
                tr_mcc_codes_repo.save(tr_mcc_code);
            }

            ArrayList<Tr_types> tr_types = Parser.ParseTr_types(uploadPath + "tr_mcc_codes.csv");
            for(Tr_types tr_type : tr_types) {
                tr_types_repo.save(tr_type);
            }

            ArrayList<Transaction> transactions = Parser.ParseTransaction(uploadPath + "transactions_cut.csv");
            for(Transaction transaction : transactions) {
                transactionRepo.save(transaction);
            }
        } else {
            return "home";
        }
        return "CSVtoDB";
    }

    @PostMapping("/median")
    public String median(Model model, @RequestParam String action) {
        if(action.equals("confirm")) {
            Iterable<Transaction> transactions = transactionRepo.findAll();
            List<Double> amounts = new ArrayList<>();
            for(Transaction transaction : transactions) {
                amounts.add(transaction.getAmount());
            }
            Collections.sort(amounts);
            int length = amounts.size();
            Double median;
            if(length % 2 != 0) {
                median = amounts.get(length/2);
            } else {
                median = (amounts.get(length/2) + amounts.get(length/2 - 1))/2;
            }
            model.addAttribute("median", median);
            return "median";
        } else {
            return "home";
        }
    }

    @PostMapping("/notEmpty")
    public String notEmpty(Model model, @RequestParam String action, @RequestParam String field, @RequestParam String searchedValue) {
        if(action.equals("confirm")) {
            Iterable<Transaction> transactions = transactionRepo.findAll();
            List<Transaction> result = new ArrayList<>();

            for(Transaction transaction : transactions) {
                if(transaction.getTr_datetime() != null && transaction.getTerm_id() != null) {
                    if(field.trim().equals("customer_id")) {
                        if(transaction.getCustomer_id() == Integer.parseInt(searchedValue)) {
                            result.add(transaction);
                        }
                    } else if(field.trim().equals("tr_datetime")) {
                        if(transaction.getTr_datetime().equals(searchedValue)) {
                            result.add(transaction);
                        }
                    } else if(field.trim().equals("mcc_code")) {
                        if(transaction.getMcc_code() == Integer.parseInt(searchedValue)) {
                            result.add(transaction);
                        }
                    } else if(field.trim().equals("tr_type")) {
                        if(transaction.getTr_type() == Integer.parseInt(searchedValue)) {
                            result.add(transaction);
                        }
                    } else if(field.trim().equals("amount")) {
                        if (transaction.getTr_type() == Double.parseDouble(searchedValue)) {
                            result.add(transaction);
                        }
                    } else if(field.trim().equals("term_id")) {
                        if (transaction.getTerm_id().equals(searchedValue)) {
                            result.add(transaction);
                        }
                    }
                }
            }
            boolean exists = true;
            if(result.isEmpty()) {
                exists = false;
            }
            model.addAttribute("result", result);
            model.addAttribute("exists", exists);
            return "notEmpty";
        } else {
            return "home";
        }
    }

    @PostMapping("/medianUnique")
    public String medianUnique(Model model, @RequestParam String action) {
        if(action.equals("confirm")) {
            Iterable<Transaction> Itransactions = transactionRepo.findAll();

            List<Transaction> transactions = Lists.newArrayList(Itransactions);
            Collections.reverse(transactions);

            int amountsNumber = transactions.size();

            for(int i = 0; i < amountsNumber; i++) {
                for(int j = i + 1; j < amountsNumber; j++) {
                    if(transactions.get(i).getMcc_code() == transactions.get(j).getMcc_code()
                    && transactions.get(i).getTr_type() == transactions.get(j).getTr_type()) {
                        transactions.get(j).setAmount(0);
                    }
                }
            }

            for(int i = 0; i < transactions.size(); i++) {
                if(transactions.get(i).getAmount() == 0) {
                    transactions.remove(i);
                }
            }

            amountsNumber = transactions.size();
            Double median;
            if(amountsNumber % 2 != 0) {
                median = transactions.get(amountsNumber/2).getAmount();
            } else {
                median = (transactions.get(amountsNumber/2).getAmount() + transactions.get(amountsNumber/2 - 1).getAmount())/2;
            }
            model.addAttribute("median", median);
            return "median";
        } else {
            return "home";
        }
    }
}
