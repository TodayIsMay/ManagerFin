package com.example.Manager.controllers;

import com.example.Manager.dto.TransactionDto;
import com.example.Manager.entities.Transaction;
import com.example.Manager.services.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAll() {
        return transactionService.findAll();
    }

    @PostMapping
    public void addTransaction(@RequestBody TransactionDto transaction) {
        transactionService.save(transaction);
    }
}