package com.example.Manager.services;

import com.example.Manager.dto.TransactionDto;
import com.example.Manager.entities.Aim;
import com.example.Manager.entities.Transaction;
import com.example.Manager.repositories.TransactionRepository;
import com.example.Manager.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final WalletService walletService;
    private final AimService aimService;

    public TransactionService(TransactionRepository repository, UserRepository userRepository, WalletService walletService, AimService aimService) {
        this.transactionRepository = repository;
        this.userRepository = userRepository;
        this.walletService = walletService;
        this.aimService = aimService;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Transactional
    public Transaction save(TransactionDto transactionDto, Integer walletId) {
        Transaction newTransaction = mapToEntity(transactionDto);
        newTransaction.setWalletId(walletId);

        walletService.addTransaction(newTransaction, walletId);
        aimService.addTransaction(transactionDto.getAimId(), transactionDto);
        return transactionRepository.save(newTransaction);
    }

    public void delete(Integer transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        walletService.deleteTransactionFromWallet(transaction.getWalletId(), transaction);
        transactionRepository.deleteById(transactionId);
    }

    public List<TransactionDto> getWalletTransactions(Integer walletId) {
        List<Transaction> walletTransactions = transactionRepository.getTransactionsByWalletId(walletId);

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
        transaction.setWalletId(transaction.getWalletId());
        transaction.setAimId(transactionDto.getAimId());
        return transaction;
    }

    public TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(
            transaction.getId(),
            transaction.getUser().getUsername(),
            transaction.getAmount(),
            transaction.getType(),
            transaction.getDate(),
            transaction.getAimId());
    }
}