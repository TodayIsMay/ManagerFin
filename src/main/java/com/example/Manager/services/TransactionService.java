package com.example.Manager.services;

import com.example.Manager.dto.TransactionDto;
import com.example.Manager.entities.Transaction;
import com.example.Manager.repositories.TransactionRepository;
import com.example.Manager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction save(TransactionDto transaction) {
        return repository.save(mapToEntity(transaction));
    }

    public Transaction mapToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setUser(userRepository.findByUsername((transactionDto.getUsername())).orElseThrow());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType());
        transaction.setDate(transactionDto.getDate());
        return transaction;
    }
}