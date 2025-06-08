package com.projet.project_e_banking.Dto.EspaceBanque;

import java.time.LocalDate;

public class ContractRequest {
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double amount;
    private String status;
    private Long userId;
    private Long agentId;
    private Long bankId;

    // Getters
    public String getType() {
        return type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public Long getBankId() {
        return bankId;
    }

    // Setters
    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
}