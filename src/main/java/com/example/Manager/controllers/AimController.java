package com.example.Manager.controllers;

import com.example.Manager.dto.AimDto;
import com.example.Manager.services.AimService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aim")
public class AimController {
    private final AimService aimService;

    public AimController(AimService aimService) {
        this.aimService = aimService;
    }

    @PostMapping("/{ownerId}")
    public AimDto createAim(@RequestBody AimDto aimDto, @PathVariable Integer ownerId) {
        return aimService.createAim(aimDto, ownerId);
    }
}