package com.example.Manager.services;

import com.example.Manager.dto.AimDto;
import com.example.Manager.dto.TransactionDto;
import com.example.Manager.entities.Aim;
import com.example.Manager.entities.User;
import com.example.Manager.repositories.AimRepository;
import com.example.Manager.repositories.UserRepository;
import com.example.Manager.utils.TransactionType;
import org.springframework.stereotype.Service;

@Service
public class AimService {
    private final AimRepository aimRepository;
    private final UserRepository userRepository;

    public AimService(AimRepository aimRepository, UserRepository userRepository) {
        this.aimRepository = aimRepository;
        this.userRepository = userRepository;
    }

    public AimDto createAim(AimDto aimDto, Integer userId) {
        User owner = userRepository.findById(userId).orElseThrow();
        Aim aim = mapToEntity(aimDto);
        aim.setOwner(owner);

        aimRepository.save(aim);
        return aimDto;
    }

    public void addTransaction(Integer aimId, TransactionDto transactionDto) {
        if (transactionDto.getType() == TransactionType.EXPENSE) {
            throw new IllegalArgumentException("Expense transactions are not supported with the aims");
        }

        Aim aim = aimRepository.findById(aimId).orElseThrow();
        aim.setCurrentAmount(aim.getCurrentAmount() + transactionDto.getAmount());
        aimRepository.save(aim);
    }

    public Aim mapToEntity(AimDto aimDto) {
        Aim aim = new Aim();

        aim.setName(aimDto.name());
        aim.setCurrentAmount(aimDto.currentAmount());
        aim.setTargetAmount(aimDto.targetAmount());

        return aim;
    }
}