package com.projet.project_e_banking.Dto.EspaceClient;

public class RechargeRequest {
    String compteUser;
    String operatorName;
    String phone;
    Double montant;
    String code;

    public RechargeRequest() {
    }

    public RechargeRequest(String compteUser, String operatorName, String phone, Double montant, String code) {
        this.compteUser = compteUser;
        this.operatorName = operatorName;
        this.phone = phone;
        this.montant = montant;
        this.code = code;
    }

    public String getCompteUser() {
        return compteUser;
    }

    public void setCompteUser(String compteUser) {
        this.compteUser = compteUser;
    }


    public String getPhone() {
        return phone;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}