package com.projet.project_e_banking.Dto.EspaceClient;

public class SupportRequest {
    String message;
    String typeSupport;

    public SupportRequest() {
    }

    public SupportRequest(String message, String typeSupport) {
        this.message = message;
        this.typeSupport = typeSupport;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeSupport() {
        return typeSupport;
    }

    public void setTypeSupport(String typeSupport) {
        this.typeSupport = typeSupport;
    }
}

