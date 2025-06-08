package com.projet.project_e_banking.Repository.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.SmsSent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsSentRepository extends JpaRepository<SmsSent, Long> {
}
