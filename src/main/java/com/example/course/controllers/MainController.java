package com.example.course.controllers;

import com.example.course.Parser;
import com.example.course.models.Transaction;
import com.example.course.repos.Gender_train_Repo;
import com.example.course.repos.Tr_mcc_codes_Repo;
import com.example.course.repos.Tr_types_Repo;
import com.example.course.repos.TransactionRepo;
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

    @GetMapping("/transactions")
    public String transactions(Model model) throws IOException {
        ArrayList<Transaction> transactions = Parser.ParseTransactionCSV("./DataCSV/transactions_cut.csv");
        Iterable<Transaction> transactions1 = transactionRepo.findAll();
        model.addAttribute("transactions", transactions);
        model.addAttribute("transactions1", transactions1);
        return "transactions";
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
            return "CSVtoDB";
        } else {
            return "home";
        }
    }
}
