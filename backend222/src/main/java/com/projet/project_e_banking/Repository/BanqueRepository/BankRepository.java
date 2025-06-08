package com.projet.project_e_banking.Repository.BanqueRepository;

import com.projet.project_e_banking.Model.EspaceBanque.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {
}
