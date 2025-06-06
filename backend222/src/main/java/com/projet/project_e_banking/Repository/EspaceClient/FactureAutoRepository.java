package com.projet.project_e_banking.Repository.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.FactureAuto;
import com.projet.project_e_banking.Utilis.StatutFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface FactureAutoRepository extends JpaRepository<FactureAuto,Long> {
   public List<FactureAuto> findByStatut(StatutFacture statutFacture);
}
