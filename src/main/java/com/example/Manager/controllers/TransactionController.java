package com.example.Manager.controllers;

import com.example.Manager.dto.TransactionDto;
import com.example.Manager.entities.Transaction;
import com.example.Manager.services.TransactionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/{walletId}")
    public Transaction addTransaction(@RequestBody TransactionDto transaction, @PathVariable Integer walletId) {
        return transactionService.save(transaction, walletId);
    }

    @GetMapping("/{walletId}")
    public List<TransactionDto> getWalletTransactions(@PathVariable Integer walletId) {
        return transactionService.getWalletTransactions(walletId);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Integer id) {
        transactionService.delete(id);
    }
}