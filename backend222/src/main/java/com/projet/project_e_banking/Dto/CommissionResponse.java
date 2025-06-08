// src/main/java/com/projet/project_e_banking/Dto/CommissionResponse.java
package com.projet.project_e_banking.Dto;

public class CommissionResponse {
    private Double commission;

    public CommissionResponse(Double commission) {
        this.commission = commission;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }
}
