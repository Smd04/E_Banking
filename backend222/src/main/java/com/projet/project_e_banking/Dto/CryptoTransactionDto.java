// Update CryptoTransactionDto.java
package com.projet.project_e_banking.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CryptoTransactionDto {
    private String cryptoId;
    private String cryptoSymbol;
    private String cryptoName;
    private double amount;
    private double priceAtTransaction;
    private String transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime transactionDate;

    public CryptoTransactionDto() {
        // Default constructor needed for Jackson
    }
    // Add constructor with all fields
    public CryptoTransactionDto(String cryptoId, String cryptoSymbol, String cryptoName,
                                double amount, double priceAtTransaction,
                                String transactionType, LocalDateTime transactionDate) {
        this.cryptoId = cryptoId;
        this.cryptoSymbol = cryptoSymbol;
        this.cryptoName = cryptoName;
        this.amount = amount;
        this.priceAtTransaction = priceAtTransaction;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
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