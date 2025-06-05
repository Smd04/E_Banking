package com.projet.project_e_banking.Model.EspaceClient;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String message;
    boolean read;

    @ManyToMany
    @JoinTable(
            name = "user_notification", // nom de la table d'association
            joinColumns = @JoinColumn(name = "notification_id"), // colonne de jointure pour Student
            inverseJoinColumns = @JoinColumn(name = "user_id") // colonne de jointure pour Course
    )
    List<User> user;

    public Notification() {
    }

    public Notification(Long id, String message, boolean read, List<User> user) {
        this.id = id;
        this.message = message;
        this.read = read;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
