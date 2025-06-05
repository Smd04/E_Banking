package com.projet.project_e_banking.Dto.EspaceClient;

public class WebAuthnVerificationRequest {

    private String credentialId;
    private String clientDataJSON;
    private String authenticatorData;

    private String signature;


    public WebAuthnVerificationRequest() {}

    public WebAuthnVerificationRequest(String credentialId, String authenticatorData, String clientDataJSON, String signature) {
        this.credentialId = credentialId;
        this.authenticatorData = authenticatorData;
        this.clientDataJSON = clientDataJSON;
        this.signature = signature;
    }


    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getClientDataJSON() {
        return clientDataJSON;
    }

    public void setClientDataJSON(String clientDataJSON) {
        this.clientDataJSON = clientDataJSON;
    }

    public String getAuthenticatorData() {
        return authenticatorData;
    }

    public void setAuthenticatorData(String authenticatorData) {
        this.authenticatorData = authenticatorData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


}
