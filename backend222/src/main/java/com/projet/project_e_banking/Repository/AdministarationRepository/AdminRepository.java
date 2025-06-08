package com.projet.project_e_banking.Repository.AdministarationRepository;

import com.projet.project_e_banking.Model.EspaceAdministration.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  AdminRepository extends JpaRepository<Administrator,Long> {
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
}
