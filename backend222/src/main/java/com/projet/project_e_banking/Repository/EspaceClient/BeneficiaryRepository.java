package com.projet.project_e_banking.Repository.EspaceClient;

import com.projet.project_e_banking.Model.EspaceAdministration.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    public boolean existsBeneficiaryByAccountNumber(String accountNumber);
}
