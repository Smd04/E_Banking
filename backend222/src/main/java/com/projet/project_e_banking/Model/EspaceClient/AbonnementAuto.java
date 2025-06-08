package com.projet.project_e_banking.Model.EspaceClient;

import com.projet.project_e_banking.Utilis.StatutFacture;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "abonnement_auto")
public class AbonnementAuto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String typeAbonnement;
    @Column(unique = true)
    private String referenceClient;

    @Enumerated(EnumType.STRING)
    private StatutFacture statut;
    private LocalDateTime dateProchainCheck;
    private String rib;
    private Double prix;

    public AbonnementAuto() {
    }

    public AbonnementAuto(Long id, Long userId, String typeAbonnement, String referenceClient, StatutFacture statut, LocalDateTime dateProchainCheck, String rib, Double prix) {
        this.id = id;
        this.userId = userId;
        this.typeAbonnement = typeAbonnement;
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

    public String getTypeAbonnement() {
        return typeAbonnement;
    }

    public void setTypeAbonnement(String typeAbonnement) {
        this.typeAbonnement = typeAbonnement;
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
