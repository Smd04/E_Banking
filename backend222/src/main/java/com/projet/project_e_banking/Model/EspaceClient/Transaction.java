package com.projet.project_e_banking.Model.EspaceClient;

import com.projet.project_e_banking.Model.EspaceAdministration.Devise;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String type;
    private LocalDateTime timestamp;
    private String status;
    private String description;
    private String compteDest;


    @ManyToOne
    private Account account;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "devise_id")
    private Devise devise;


    public Transaction() {
    }

    public Transaction(Long id, Double amount, String type, LocalDateTime timestamp, String status, Account account, User user,Devise devise) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.status = status;
        this.account = account;
        this.user = user;
        this.devise=devise;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompteDest() {
        return compteDest;
    }

    public void setCompteDest(String compteDest) {
        this.compteDest = compteDest;
    }
}
