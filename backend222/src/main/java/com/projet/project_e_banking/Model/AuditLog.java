package com.projet.project_e_banking.Model;

import java.time.LocalDateTime;

public class AuditLog {
    Long id;
    String action;
    String performedBy;
    LocalDateTime timestamp;

    public AuditLog() {
    }

    public AuditLog(Long id, String action, String performedBy, LocalDateTime timestamp) {
        this.id = id;
        this.action = action;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
