package com.projet.project_e_banking.Model.EspaceClient;

import com.projet.project_e_banking.Model.EspaceAdministration.Beneficiary;
import com.projet.project_e_banking.Model.EspaceAdministration.CryptoTransaction;
import com.projet.project_e_banking.Model.EspaceAdministration.SystemSettings;
import com.projet.project_e_banking.Model.EspaceBanque.Agent;
import com.projet.project_e_banking.Model.EspaceBanque.Contract;
import com.projet.project_e_banking.Utilis.Role;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public  class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;

    String name;
    String email;
    String password;
    String phone;
    String address;
    String city;
    String status;
    Role role;
    @OneToMany(mappedBy = "user")
    List<Account> accounts;
    @OneToMany(mappedBy = "user")
    List<Transaction> transactions;
    @ManyToMany(mappedBy = "user")
    List<Notification> notifications;
    @OneToMany(mappedBy = "user")
    List<Beneficiary> beneficiaries;
    @OneToMany(mappedBy = "user")
    List<AuthenticationToken> authenticationTokens;
    @ManyToOne
    Agent agent;
    @OneToMany(mappedBy = "user")
    List<Contract> contacts;
    public User() {
    }

    @OneToOne(mappedBy = "user")
    private SystemSettings settings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CryptoTransaction> cryptoTransactions;

    public User(Long id, String username,String name, String email, String password, String phone, String address, String city, String status, Role role, List<Account> accounts, List<Transaction>transactions, List<Notification> notifications, List<Beneficiary> beneficiaries, List<AuthenticationToken> authenticationTokens,Agent agent, List<Contract> contracts) {
        this.id = id;
        this.username=username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.status = status;
        this.role = role;
        this.accounts = accounts;
        this.transactions = transactions;
        this.notifications = notifications;
        this.beneficiaries = beneficiaries;
        this.authenticationTokens = authenticationTokens;
        this.agent = agent;
        this.contacts = contracts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public List<AuthenticationToken> getAuthenticationTokens() {
        return authenticationTokens;
    }

    public void setAuthenticationTokens(List<AuthenticationToken> authenticationTokens) {
        this.authenticationTokens = authenticationTokens;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<Contract> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contract> contacts) {
        this.contacts = contacts;
    }

    public SystemSettings getSettings() {
        return settings;
    }

    public void setSettings(SystemSettings settings) {
        this.settings = settings;
    }

    public List<CryptoTransaction> getCryptoTransactions() {
        return cryptoTransactions;
    }

    public void setCryptoTransactions(List<CryptoTransaction> cryptoTransactions) {
        this.cryptoTransactions = cryptoTransactions;
    }
}


