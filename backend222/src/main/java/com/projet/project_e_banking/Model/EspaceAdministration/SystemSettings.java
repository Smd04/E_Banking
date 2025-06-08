package com.projet.project_e_banking.Model.EspaceAdministration;

import com.projet.project_e_banking.Model.EspaceClient.User;
import jakarta.persistence.*;

@Entity
public class SystemSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double commissionRate;
    private Double maxTransactionAmount;
    private Double transferLimit;
    private boolean cryptoEnabled;
    private boolean referralEnabled;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Double getMaxTransactionAmount() {
        return maxTransactionAmount;
    }

    public void setMaxTransactionAmount(Double maxTransactionAmount) {
        this.maxTransactionAmount = maxTransactionAmount;
    }

    public Double getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(Double transferLimit) {
        this.transferLimit = transferLimit;
    }

    public boolean isCryptoEnabled() {
        return cryptoEnabled;
    }

    public void setCryptoEnabled(boolean cryptoEnabled) {
        this.cryptoEnabled = cryptoEnabled;
    }

    public boolean isReferralEnabled() {
        return referralEnabled;
    }

    public void setReferralEnabled(boolean referralEnabled) {
        this.referralEnabled = referralEnabled;
    }
}
