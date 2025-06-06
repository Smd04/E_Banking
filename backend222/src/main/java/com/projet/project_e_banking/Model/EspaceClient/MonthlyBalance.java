package com.projet.project_e_banking.Model.EspaceClient;

import jakarta.persistence.*;


@Entity
@Table(
        name = "monthlybalance",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"account_id", "mois"})
        }
)
public class MonthlyBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    private Double revenus = 0.0;
    private Double depenses = 0.0;

    private int mois;

    public MonthlyBalance() {
    }

    public MonthlyBalance(Long id, Account account, Double revenus, Double depenses, int mois) {
        this.id = id;
        this.account = account;
        this.revenus = revenus;
        this.depenses = depenses;
        this.mois = mois;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getRevenus() {
        return revenus;
    }

    public void setRevenus(Double revenus) {
        this.revenus = revenus;
    }

    public Double getDepenses() {
        return depenses;
    }

    public void setDepenses(Double depenses) {
        this.depenses = depenses;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }
}





