package com.projet.project_e_banking.Dto;

import jakarta.validation.constraints.NotNull;

public class ReferralRequest {
    @NotNull(message = "Referring user ID cannot be null")
    private Long referringUserId;
    private Long referredUserId;

    // Constructors
    public ReferralRequest() {
    }

    public ReferralRequest(Long referringUserId, Long referredUserId) {
        this.referringUserId = referringUserId;
        this.referredUserId = referredUserId;
    }

    // Getters and Setters
    public Long getReferringUserId() {
        return referringUserId;
    }

    public void setReferringUserId(Long referringUserId) {
        this.referringUserId = referringUserId;
    }

    public Long getReferredUserId() {
        return referredUserId;
    }

    public void setReferredUserId(Long referredUserId) {
        this.referredUserId = referredUserId;
    }
}