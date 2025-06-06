package com.projet.project_e_banking.Dto.EspaceClient;


import com.projet.project_e_banking.Model.EspaceClient.Transaction;

import java.time.LocalDateTime;

public class OtpTransaction {

    private String phoneNumber;
    private String code;
    private LocalDateTime expirationTime;
    private Transaction transaction;
    public OtpTransaction() {}

    public OtpTransaction(String phoneNumber, String code, LocalDateTime expirationTime, Transaction transaction) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.expirationTime = expirationTime;
        this.transaction = transaction;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}

