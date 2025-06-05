package com.projet.project_e_banking.Dto.EspaceClient;

import lombok.Data;

@Data
public class RegisterCrentialRequest {
    private String credentialId;
    private String publicKey;
    private Long userId;
}


