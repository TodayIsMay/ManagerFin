package com.example.Manager.services;

import com.example.Manager.dto.TransactionDto;
import com.example.Manager.dto.WalletDto;
import com.example.Manager.entities.Transaction;
import com.example.Manager.entities.Wallet;
import com.example.Manager.repositories.WalletRepository;
import com.example.Manager.utils.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getWalletById(Integer id) {
        return walletRepository.findById(id).orElseThrow();
    }

    public void createWallet(WalletDto walletDto) {
        walletRepository.save(mapToEntity(walletDto));
    }

    @Transactional
    public void addTransaction(Transaction transaction, Integer walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();

        Double initialAmount = wallet.getAmount();
        double result;

        if (transaction.getType() == TransactionType.EXPENSE) {
            result = initialAmount - transaction.getAmount();
        } else {
            result = initialAmount + transaction.getAmount();
        }

        wallet.setAmount(result);

        walletRepository.save(wallet);
    }

    public static WalletDto mapToDto(Wallet wallet) {
        return new WalletDto(wallet.getId(), wallet.getName(), wallet.getAmount());
    }

    public static Wallet mapToEntity(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setName(walletDto.getName());
        wallet.setAmount(walletDto.getAmount());

        return wallet;
    }
}
