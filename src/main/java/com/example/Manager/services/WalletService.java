package com.example.Manager.services;

import com.example.Manager.dto.WalletDto;
import com.example.Manager.entities.Transaction;
import com.example.Manager.entities.Wallet;
import com.example.Manager.repositories.WalletRepository;
import com.example.Manager.utils.TransactionType;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final Integer walletInitialId;

    public WalletService(WalletRepository walletRepository, @Value("${wallets.initial-id}") Integer walletInitialId ) {
        this.walletRepository = walletRepository;
        this.walletInitialId = walletInitialId;

        if (getWalletById(walletInitialId) == null) {
            WalletDto initialWallet = new WalletDto(walletInitialId, "baseWallet", 0d);
            walletRepository.save(mapToEntity(initialWallet));
        }
    }

    @PostConstruct
    public void test() {
        System.out.println("Wallet initial id = " + walletInitialId);
        System.out.println("Object number = " + this.hashCode());
    }

    public Wallet getWalletById(Integer id) {
        return walletRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Wallet with id " + id + " not found"));
    }

    public void createWallet(WalletDto walletDto) {
        walletRepository.save(mapToEntity(walletDto));
    }

    @Transactional
    public void addTransaction(Transaction transaction, Integer walletId) {
        Wallet wallet = walletRepository.findById(walletId)
            .orElseThrow(() -> new EntityNotFoundException("Wallet with id " + walletId + " not found"));

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

    public void deleteTransactionFromWallet(Integer walletId, Transaction transaction) {
        Wallet wallet = walletRepository.findById(walletId)
            .orElseThrow(() -> new EntityNotFoundException("Wallet with id " + walletId + " not found"));

        Double initialAmount = wallet.getAmount();
        double result;

        if (transaction.getType() == TransactionType.EXPENSE) {
            result = initialAmount + transaction.getAmount();
        } else {
            result = initialAmount - transaction.getAmount();
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
