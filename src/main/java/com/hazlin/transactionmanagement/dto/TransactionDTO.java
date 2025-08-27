package com.hazlin.transactionmanagement.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;

    @NotNull(message = "Amount cannot be null")
    private Double amount;

    private String type;

    private String description;

    @NotNull(message = "Timestamp cannot be null")
    private LocalDateTime timestamp;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, Double amount, String type, String description, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
