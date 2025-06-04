package com.projet.project_e_banking.Model.EspaceAdministration;

import com.projet.project_e_banking.Model.EspaceClient.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class SupportMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String message;
    Date timestamp;
    private String type ;
    @ManyToOne
    User user;
    @ManyToOne
    Administrator admin;

    public SupportMessage() {
    }

    public SupportMessage(Long id, String message, Date timestamp, String type ,User user, Administrator admin) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.type=type;
        this.user = user;
        this.admin = admin;
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }
}
