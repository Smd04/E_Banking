package com.projet.project_e_banking.Model.ModelSystemParainage;

import jakarta.persistence.*;

@Entity
public class RefferalTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private Long referringUserId;

    @Column(nullable = false)
    private Long referredUserId;

    public RefferalTracking() {
    }

    public RefferalTracking(Long id, Long referringUserId, Long referredUserId) {
        this.id = id;
        this.referringUserId = referringUserId;
        this.referredUserId = referredUserId;
    }

    // Getters and Setters (updated names)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReferringUserId() {
        return referringUserId;
    }

    public void setReferringUserId(Long referringUserId) {
        this.referringUserId = referringUserId;
    }

    public Long getReferredUserId() {
        return referredUserId;
    }

    public void setReferredUserId(Long referredUserId) {
        this.referredUserId = referredUserId;
    }
}