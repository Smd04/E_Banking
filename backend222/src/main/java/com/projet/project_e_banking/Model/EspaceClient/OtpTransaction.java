package com.projet.project_e_banking.Model.EspaceClient;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_transactions")
public class OtpTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    @Column(unique = true)
    private String code;

    private LocalDateTime expiryTime;

    private boolean verified = false;

    @OneToOne(cascade = CascadeType.ALL)
    private Transaction transaction;

    public OtpTransaction() {
    }

    public OtpTransaction(Long id, String phone, String code, LocalDateTime expiryTime, boolean verified, Transaction transaction) {
        this.id = id;
        this.phone = phone;
        this.code = code;
        this.expiryTime = expiryTime;
        this.verified = verified;
        this.transaction = transaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}

