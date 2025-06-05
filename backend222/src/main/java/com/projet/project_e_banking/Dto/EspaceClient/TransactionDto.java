package com.projet.project_e_banking.Dto.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class TransactionDto {

    private Long id;
    private Double amount;
    private String type;
    private LocalDateTime timestamp;
    private String status;
    private Long accountId;
    private Long userId;
    private String compteDest;
    private String Description;

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompteDest() {
        return compteDest;
    }

    public void setCompteDest(String compteDest) {
        this.compteDest = compteDest;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", amount=" + amount +
                ", date='" + timestamp +
                  ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", accountId=" + accountId +
                ", userId=" + userId +
                ", compteDest=" + compteDest +
                '\'' +
                // autres champs formatés
                '}';
    }
}
