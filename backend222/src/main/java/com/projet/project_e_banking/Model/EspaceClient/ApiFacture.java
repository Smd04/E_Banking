package com.projet.project_e_banking.Model.EspaceClient;

import jakarta.persistence.*;

@Entity
public class ApiFacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String typeFacture;

    public ApiFacture() {
    }

    public ApiFacture(Long id, String reference, String typeFacture) {
        this.id = id;
        this.reference = reference;
       this.typeFacture = typeFacture;
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

    public String getTypeFacture() {
        return typeFacture;
    }

    public void setTypeFacture(String typeFacture) {
        this.typeFacture = typeFacture;
    }
}
