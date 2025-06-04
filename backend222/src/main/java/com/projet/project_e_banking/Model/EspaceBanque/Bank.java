package com.projet.project_e_banking.Model.EspaceBanque;

import com.projet.project_e_banking.Model.EspaceAdministration.Administrator;
import com.projet.project_e_banking.Model.EspaceAdministration.Devise;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String phoneNumber;
    private String email;
    private String website;
    private String logoUrl;
    private String licence;  //licence
    private String supportHours;
    private boolean cryptoEnabled;
    private boolean biometricSupport;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    String address;
    @OneToMany(mappedBy = "bank")
    List<Agent> agents;
    @OneToMany(mappedBy = "bank")
    List<Contract> contracts;
    @OneToMany(mappedBy = "bank")
    List<Administrator> administrators;

    @ManyToMany
    @JoinTable(name = "banque_devise",
            joinColumns = @JoinColumn(name = "banque_id"),
            inverseJoinColumns = @JoinColumn(name = "devise_id"))
    private List<Devise> devises;

    public Bank(List<Administrator> administrators, List<Contract> contracts, List<Agent> agents, String address, LocalDateTime updatedAt, LocalDateTime createdAt, boolean biometricSupport, boolean cryptoEnabled, String supportHours, String licence, String logoUrl, String website, String email, String phoneNumber, Long id) {
        this.administrators = administrators;
        this.contracts = contracts;
        this.agents = agents;
        this.address = address;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.biometricSupport = biometricSupport;
        this.cryptoEnabled = cryptoEnabled;
        this.supportHours = supportHours;
        this.licence = licence;
        this.logoUrl = logoUrl;
        this.website = website;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }
    public Bank(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getSupportHours() {
        return supportHours;
    }

    public void setSupportHours(String supportHours) {
        this.supportHours = supportHours;
    }

    public boolean isCryptoEnabled() {
        return cryptoEnabled;
    }

    public void setCryptoEnabled(boolean cryptoEnabled) {
        this.cryptoEnabled = cryptoEnabled;
    }

    public boolean isBiometricSupport() {
        return biometricSupport;
    }

    public void setBiometricSupport(boolean biometricSupport) {
        this.biometricSupport = biometricSupport;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public List<Administrator> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<Administrator> administrators) {
        this.administrators = administrators;
    }
}
