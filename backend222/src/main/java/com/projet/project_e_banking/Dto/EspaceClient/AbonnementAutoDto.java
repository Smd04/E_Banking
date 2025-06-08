package com.projet.project_e_banking.Dto.EspaceClient;

import com.projet.project_e_banking.Utilis.StatutFacture;

import java.time.LocalDateTime;

public class AbonnementAutoDto {
    private String type;
    private String compteUser;
    private String referenceClient;
    private LocalDateTime dateProchainCheck;


    public AbonnementAutoDto() {
    }

    public AbonnementAutoDto(String type, String compteUser, String referenceClient, LocalDateTime dateProchainCheck) {
        this.type = type;
        this.compteUser = compteUser;
        this.referenceClient = referenceClient;
        this.dateProchainCheck = dateProchainCheck;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReferenceClient() {
        return referenceClient;
    }

    public void setReferenceClient(String referenceClient) {
        this.referenceClient = referenceClient;
    }



    public String getCompteUser() {
        return compteUser;
    }

    public void setCompteUser(String compteUser) {
        this.compteUser = compteUser;
    }

    public LocalDateTime getDateProchainCheck() {
        return dateProchainCheck;
    }

    public void setDateProchainCheck(LocalDateTime dateProchainCheck) {
        this.dateProchainCheck = dateProchainCheck;
    }
}

