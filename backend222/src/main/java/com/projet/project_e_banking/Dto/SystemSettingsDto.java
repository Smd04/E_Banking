package com.projet.project_e_banking.Dto;


public class SystemSettingsDto {
    private Long id;
    private Double commissionRate;
    private Double maxTransactionAmount;
    private Double transferLimit;
    private boolean cryptoEnabled;
    private boolean referralEnabled;
    private Long userId;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getCommissionRate() { return commissionRate; }
    public void setCommissionRate(Double commissionRate) { this.commissionRate = commissionRate; }
    public Double getMaxTransactionAmount() { return maxTransactionAmount; }
    public void setMaxTransactionAmount(Double maxTransactionAmount) { this.maxTransactionAmount = maxTransactionAmount; }
    public Double getTransferLimit() { return transferLimit; }
    public void setTransferLimit(Double transferLimit) { this.transferLimit = transferLimit; }
    public boolean isCryptoEnabled() { return cryptoEnabled; }
    public void setCryptoEnabled(boolean cryptoEnabled) { this.cryptoEnabled = cryptoEnabled; }
    public boolean isReferralEnabled() { return referralEnabled; }
    public void setReferralEnabled(boolean referralEnabled) { this.referralEnabled = referralEnabled; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}