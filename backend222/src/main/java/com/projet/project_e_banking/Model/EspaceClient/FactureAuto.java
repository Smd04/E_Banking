package com.projet.project_e_banking.Model.EspaceClient;

import com.projet.project_e_banking.Utilis.StatutFacture;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "facture_auto")
public class FactureAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String typeFacture;
    @Column(unique = true)
    private String referenceClient;

    @Enumerated(EnumType.STRING)
    private StatutFacture statut;
    private LocalDateTime dateProchainCheck;
    private String rib;
    private Double prix;


    public FactureAuto() {
    }

    public FactureAuto(Long id, Long userId, String typeFacture, String referenceClient, StatutFacture statut,LocalDateTime dateProchainCheck,String rib,Double prix) {
        this.id = id;
        this.userId = userId;
        this.typeFacture = typeFacture;
        this.referenceClient = referenceClient;
        this.statut = statut;
        this.dateProchainCheck = dateProchainCheck;
        this.rib = rib;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTypeFacture() {
        return typeFacture;
    }

    public void setTypeFacture(String typeFacture) {
        this.typeFacture = typeFacture;
    }

    public String getReferenceClient() {
        return referenceClient;
    }

    public void setReferenceClient(String referenceClient) {
        this.referenceClient = referenceClient;
    }

    public StatutFacture getStatut() {
        return statut;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateProchainCheck() {
        return dateProchainCheck;
    }

    public void setDateProchainCheck(LocalDateTime dateProchainCheck) {
        this.dateProchainCheck = dateProchainCheck;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
}
