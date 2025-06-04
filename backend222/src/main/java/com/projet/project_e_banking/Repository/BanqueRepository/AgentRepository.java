package com.projet.project_e_banking.Repository.BanqueRepository;

import com.projet.project_e_banking.Model.EspaceBanque.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AgentRepository extends JpaRepository<Agent,Long> {
}
