package com.projet.project_e_banking.Model.EspaceClient;

import jakarta.persistence.*;

@Entity
public class ApiRecharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String phone;
    private String operateur;

    public ApiRecharge() {
    }

    public ApiRecharge(int id, String phone, String operateur) {
        this.id = id;
        this.phone = phone;
        this.operateur = operateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOperateur() {
        return operateur;
    }

    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }
}