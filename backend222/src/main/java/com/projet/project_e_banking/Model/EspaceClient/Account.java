package com.projet.project_e_banking.Model.EspaceClient;

import com.projet.project_e_banking.Model.EspaceAdministration.Devise;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String type;
    Double balance;
    String currency;
    String Status;
    String accountNumber;
    @ManyToOne
    User user;
    @OneToMany(mappedBy = "account")
    List<Transaction> transactions;
    @OneToOne
    Card card;
    @ManyToOne
    @JoinColumn(name = "devise_id")
    private Devise devise;

    public Account() {}

    public Account(Long id, String type, Double balance, String currency, String status, User user, List<Transaction> transactions, Card card,Devise devise) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.currency = currency;
        Status = status;
        this.user = user;
        this.transactions = transactions;
        this.card = card;
        this.devise= devise ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
