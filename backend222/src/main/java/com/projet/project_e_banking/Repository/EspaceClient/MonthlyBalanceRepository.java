package com.projet.project_e_banking.Repository.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.MonthlyBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyBalanceRepository extends JpaRepository<MonthlyBalance, Long> {
    public MonthlyBalance findByMoisAndAccount_Id(int mois, Long account_id);
    public boolean existsByMoisAndAccountId(int mois,Long account_id);
}
