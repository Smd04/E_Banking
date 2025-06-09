package com.projet.project_e_banking.Model.EspaceClient;

import jakarta.persistence.*;

@Entity
public class ApiAbonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String typeAbonnement;

    public ApiAbonnement() {
    }

    public ApiAbonnement(Long id, String reference, String typeAbonnement) {
        this.id = id;
        this.reference = reference;
        this.typeAbonnement = typeAbonnement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTypeAbonnement() {
        return typeAbonnement;
    }

    public void setTypeAbonnement(String typeAbonnement) {
        this.typeAbonnement = typeAbonnement;
    }
}

