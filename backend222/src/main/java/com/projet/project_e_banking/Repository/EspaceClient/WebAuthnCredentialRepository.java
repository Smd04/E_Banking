package com.projet.project_e_banking.Repository.EspaceClient;


import com.projet.project_e_banking.Model.ModelPaymentBiometrique.WebAuthCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WebAuthnCredentialRepository extends JpaRepository<WebAuthCredential,String> {
    Optional<WebAuthCredential> findByCredentialId(String credentialId);
    Optional<WebAuthCredential> findByUserId(Long userId);
}
