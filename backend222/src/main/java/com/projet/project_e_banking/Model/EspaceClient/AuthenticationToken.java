package com.projet.project_e_banking.Model.EspaceClient;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class AuthenticationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String authenticationToken;
    LocalDateTime createdAt;
    LocalDateTime expiresAt;
    @ManyToOne
    User user;

    public AuthenticationToken() {
    }

    public AuthenticationToken(Long id, String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
        this.id = id;
        this.authenticationToken = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return authenticationToken;
    }

    public void setToken(String token) {
        this.authenticationToken = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}
