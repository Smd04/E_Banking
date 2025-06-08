package com.projet.project_e_banking.Dto.EspaceClient;

import java.time.LocalDateTime;

public class VirementRequest {
    private String compteUser;
    private String compteDestinataire;
    private Double montant;
    private String motif;
    private LocalDateTime date;
    private String phoneNumber;

    public VirementRequest() {
    }

    public VirementRequest(String compteUser, String compteDestinataire, Double montant, String motif,  LocalDateTime date, String phoneNumber) {
        this.compteUser = compteUser;
        this.compteDestinataire = compteDestinataire;
        this.montant = montant;
        this.motif = motif;
        this.date = date;
        this.phoneNumber = phoneNumber;
    }

    public String getCompteUser() {
        return compteUser;
    }

    public void setCompteUser(String compteUser) {
        this.compteUser = compteUser;
    }

    public String getCompteDestinataire() {
        return compteDestinataire;
    }

    public void setCompteDestinataire(String compteDestinataire) {
        this.compteDestinataire = compteDestinataire;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
