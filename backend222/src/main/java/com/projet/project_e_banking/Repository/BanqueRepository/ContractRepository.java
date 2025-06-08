package com.projet.project_e_banking.Repository.BanqueRepository;

import com.projet.project_e_banking.Model.EspaceBanque.Contract;
import com.projet.project_e_banking.Model.EspaceClient.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByUser(User user); // pour lister les contrats d'un utilisateur
}
