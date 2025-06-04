package com.projet.project_e_banking.Dto.EspaceBanque;

public class ClientEnrolmentRequest {
    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String type;
    private Double initialDeposit;
    private String currency;
    private String status;
    private String city;
    private String address;

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public Double getInitialDeposit() {
        return initialDeposit;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
}
