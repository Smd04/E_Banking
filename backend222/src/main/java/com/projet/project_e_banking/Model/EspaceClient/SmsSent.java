package com.projet.project_e_banking.Model.EspaceClient;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sms_sent")
public class SmsSent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String message;
    private LocalDateTime sentAt;

    public SmsSent() {
    }

    public SmsSent(Long id, String phone, String message, LocalDateTime sentAt) {
        this.id = id;
        this.phone = phone;
        this.message = message;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}

