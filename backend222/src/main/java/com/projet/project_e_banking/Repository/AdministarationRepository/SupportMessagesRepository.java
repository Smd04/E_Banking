package com.projet.project_e_banking.Repository.AdministarationRepository;

import com.projet.project_e_banking.Model.EspaceAdministration.SupportMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportMessagesRepository extends JpaRepository<SupportMessage,Long> {
    List<SupportMessage> findByType(String type);
}
