package com.projet.project_e_banking.Model.EspaceClient;

import com.projet.project_e_banking.Utilis.StatutFacture;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Paiement {
    @Id
    @GeneratedValue
    private Long id;
    private String compteDebite;
    private Double montant;
    private String type;
    private StatutFacture statut;
    private String referenceFacture;
    private LocalDateTime datePaiement;

    public Paiement() {
    }

    public Paiement(Long id, String compteDebite, Double montant, String type, StatutFacture statut, String referenceFacture, Double soldeAvantPaiement, Double soldeApresPaiement, String idTransaction, LocalDateTime datePaiement) {
        this.id = id;
        this.compteDebite = compteDebite;
        this.montant = montant;
        this.type = type;
        this.statut = statut;
        this.referenceFacture = referenceFacture;
        this.datePaiement = datePaiement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompteDebite() {
        return compteDebite;
    }

    public void setCompteDebite(String compteDebite) {
        this.compteDebite = compteDebite;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getReferenceFacture() {
        return referenceFacture;
    }

    public StatutFacture getStatut() {
        return statut;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public void setReferenceFacture(String referenceFacture) {
        this.referenceFacture = referenceFacture;
    }



    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }
}
