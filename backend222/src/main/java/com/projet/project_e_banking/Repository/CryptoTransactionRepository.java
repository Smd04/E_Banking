// com.projet.project_e_banking.Repository/CryptoTransactionRepository.java
package com.projet.project_e_banking.Repository;

import com.projet.project_e_banking.Model.EspaceAdministration.CryptoTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CryptoTransactionRepository extends JpaRepository<CryptoTransaction, Long> {
    List<CryptoTransaction> findByUserId(Long userId);
}