package com.projet.project_e_banking.Repository.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.OtpTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpTransactionRepository extends JpaRepository<OtpTransaction, Long> {
    Optional<OtpTransaction> findByCodeAndVerifiedFalse(String code);
}

