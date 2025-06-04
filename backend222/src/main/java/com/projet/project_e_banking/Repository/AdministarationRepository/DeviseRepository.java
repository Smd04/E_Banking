package com.projet.project_e_banking.Repository.AdministarationRepository;

import com.projet.project_e_banking.Model.EspaceAdministration.Devise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviseRepository extends JpaRepository<Devise,Long> {
    Boolean existsByCode(String code);
    Boolean existsByName(String name);


}
