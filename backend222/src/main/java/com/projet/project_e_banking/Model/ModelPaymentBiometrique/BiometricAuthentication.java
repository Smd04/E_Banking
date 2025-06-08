package com.projet.project_e_banking.Model.ModelPaymentBiometrique;

import com.projet.project_e_banking.Model.EspaceClient.User;
import jakarta.persistence.*;

@Entity
public class BiometricAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String biometrique;
    private boolean isAuthenticated;
    @ManyToOne
    private User user;
    public BiometricAuthentication() {}
    public BiometricAuthentication(String biometrique, boolean isAuthenticated, User user) {
        this.biometrique = biometrique;
        this.isAuthenticated = isAuthenticated;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiometrique() {
        return biometrique;
    }

    public void setBiometrique(String biometrique) {
        this.biometrique = biometrique;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
