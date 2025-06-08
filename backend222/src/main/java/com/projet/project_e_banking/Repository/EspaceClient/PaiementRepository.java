package com.projet.project_e_banking.Repository.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findTop5ByCompteDebiteOrderByDatePaiementDesc(String compteDebite);
    List<Paiement> findAllByCompteDebite(String compteDebite);

}
