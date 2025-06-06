package com.projet.project_e_banking.Dto.EspaceClient;

public class OtpValidationRequest {
    private String phoneNumber;
    private String code;

    public OtpValidationRequest() {}

    public OtpValidationRequest(String phoneNumber, String code) {
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
