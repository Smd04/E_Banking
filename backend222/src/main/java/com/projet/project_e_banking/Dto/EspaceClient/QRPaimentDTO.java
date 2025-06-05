package com.projet.project_e_banking.Dto.EspaceClient;

public class QRPaimentDTO {
    String compteDest;
    String compteSource;
    Double montant;

    public String getCompteDest() {
        return compteDest;
    }

    public void setCompteDest(String compteDest) {
        this.compteDest = compteDest;
    }

    public String getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(String compteSource) {
        this.compteSource = compteSource;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
}
