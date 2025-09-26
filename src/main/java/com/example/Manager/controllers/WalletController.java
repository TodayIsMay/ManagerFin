package com.example.Manager.controllers;

import com.example.Manager.dto.WalletDto;
import com.example.Manager.services.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.Manager.services.WalletService.mapToDto;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{id}")
    public WalletDto getWalletById(@PathVariable Integer id) {
        return mapToDto(walletService.getWalletById(id));
    }

    @PostMapping
    public void createWallet(@RequestBody WalletDto walletDto) {//todo: кошелек должен создаваться обычным пользователем только на себя, на других может создать админ
        walletService.createWallet(walletDto);
    }
}