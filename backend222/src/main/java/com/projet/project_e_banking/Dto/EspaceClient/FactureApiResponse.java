package com.projet.project_e_banking.Dto.EspaceClient;

public class FactureApiResponse {
    private String reference;
    private String type;
    private double montant;
    private String dateEcheance;
    private String statut;

    public FactureApiResponse() {
    }

    public FactureApiResponse(String reference, String type, double montant, String dateEcheance, String statut) {
        this.reference = reference;
        this.type = type;
        this.montant = montant;
        this.dateEcheance = dateEcheance;
        this.statut = statut;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(String dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}