package com.example.Manager.services;

import com.example.Manager.dto.TransactionDto;
import com.example.Manager.entities.Transaction;
import com.example.Manager.entities.Wallet;
import com.example.Manager.repositories.TransactionRepository;
import com.example.Manager.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final UserRepository userRepository;
    private final WalletService walletService;

    public TransactionService(TransactionRepository repository, UserRepository userRepository, WalletService walletService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.walletService = walletService;
    }

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Transaction save(TransactionDto transactionDto, Integer walletId) {
        Transaction newTransaction = mapToEntity(transactionDto);
        newTransaction.setWalletId(walletId);

        walletService.addTransaction(newTransaction, walletId);
        return repository.save(newTransaction);
    }

    public List<TransactionDto> getWalletTransactions(Integer walletId) {
        List<Transaction> walletTransactions = repository.getTransactionsByWalletId(walletId);

        return walletTransactions
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    public Transaction mapToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setUser(userRepository.findByUsername((transactionDto.getUsername())).orElseThrow());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType());
        transaction.setDate(transactionDto.getDate());
        return transaction;
    }

    public TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(
            transaction.getId(),
            transaction.getUser().getUsername(),
            transaction.getAmount(),
            transaction.getType(),
            transaction.getDate());
    }
}