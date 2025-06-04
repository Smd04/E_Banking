package com.projet.project_e_banking.Model.EspaceBanque;

import com.projet.project_e_banking.Model.EspaceClient.User;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double amount;
    private String status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Agent agent;

    @ManyToOne
    private Bank bank;
    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }

    public Bank getBank() { return bank; }
    public void setBank(Bank bank) { this.bank = bank; }


}

