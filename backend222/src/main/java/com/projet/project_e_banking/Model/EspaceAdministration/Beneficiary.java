package com.projet.project_e_banking.Model.EspaceAdministration;

import com.projet.project_e_banking.Model.EspaceClient.User;
import jakarta.persistence.*;

@Entity
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String bankName;
    String accountNumber;
    @ManyToOne
    User user;

    public Beneficiary() {
    }

    public Beneficiary(Long id, String name, String bankName, String accountNumber, User user) {
        this.id = id;
        this.name = name;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
