package com.projet.project_e_banking.Repository.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.AbonnementAuto;
import com.projet.project_e_banking.Model.EspaceClient.FactureAuto;
import com.projet.project_e_banking.Utilis.StatutFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbonnemetRepository extends JpaRepository<AbonnementAuto,Long> {
    public List<AbonnementAuto> findByStatut(StatutFacture statutAbonnement);

}
