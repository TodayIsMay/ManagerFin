package com.example.Manager.dto;

import com.example.Manager.utils.TransactionType;

import java.time.LocalDateTime;

public class TransactionDto {
    private Integer id;
    private String username;
    private Double amount;
    private TransactionType type;
    private LocalDateTime date;

    public TransactionDto(Integer id, String username, Double amount, TransactionType type, LocalDateTime date) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}