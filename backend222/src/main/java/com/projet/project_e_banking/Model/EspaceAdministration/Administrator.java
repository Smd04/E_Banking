package com.projet.project_e_banking.Model.EspaceAdministration;

import com.projet.project_e_banking.Model.EspaceBanque.Agent;
import com.projet.project_e_banking.Model.EspaceBanque.Bank;
import com.projet.project_e_banking.Model.EspaceBanque.Contract;
import com.projet.project_e_banking.Model.EspaceClient.*;
import com.projet.project_e_banking.Utilis.Role;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Administrator extends User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String employeeId;
    String departement;
    @OneToMany(mappedBy = "admin")
    List<SupportMessage> supportMessages;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
    public Administrator() {}

    public Administrator(Long id, String username,String name, String email, String password, String phone, String address, String city, String status, Role role, List<Account> accounts, List<Transaction> transactions, List<Notification> notifications, List<Beneficiary> beneficiaries, List<AuthenticationToken> authenticationTokens, String employeeId, String departement, List<SupportMessage> supportMessages, Agent agent, List<Contract> contracts,Bank bank) {
        super(id, username,name, email, password, phone, address, city, status, role, accounts, transactions, notifications, beneficiaries, authenticationTokens,agent,contracts);
        this.employeeId = employeeId;
        this.departement = departement;
        this.supportMessages = supportMessages;
        this.bank=bank;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public List<SupportMessage> getSupportMessages() {
        return supportMessages;
    }

    public void setSupportMessages(List<SupportMessage> supportMessages) {
        this.supportMessages = supportMessages;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
