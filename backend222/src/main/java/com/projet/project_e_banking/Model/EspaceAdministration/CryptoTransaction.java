// com.projet.project_e_banking.Model.EspaceClient/CryptoTransaction.java
package com.projet.project_e_banking.Model.EspaceAdministration;

import com.projet.project_e_banking.Model.EspaceClient.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CryptoTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String cryptoId;
    private String cryptoSymbol;
    private String cryptoName;
    private double amount;
    private double priceAtTransaction;
    private String transactionType; // "BUY" or "SELL"
    private LocalDateTime transactionDate;

    // Constructors, getters and setters
    public CryptoTransaction() {}

    public CryptoTransaction(User user, String cryptoId, String cryptoSymbol, String cryptoName,
                             double amount, double priceAtTransaction, String transactionType) {
        this.user = user;
        this.cryptoId = cryptoId;
        this.cryptoSymbol = cryptoSymbol;
        this.cryptoName = cryptoName;
        this.amount = amount;
        this.priceAtTransaction = priceAtTransaction;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(String cryptoId) {
        this.cryptoId = cryptoId;
    }

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }

    public void setCryptoSymbol(String cryptoSymbol) {
        this.cryptoSymbol = cryptoSymbol;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public void setCryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPriceAtTransaction() {
        return priceAtTransaction;
    }

    public void setPriceAtTransaction(double priceAtTransaction) {
        this.priceAtTransaction = priceAtTransaction;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}