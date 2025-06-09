package com.projet.project_e_banking.Repository.EspaceClient;


import com.projet.project_e_banking.Model.EspaceClient.ApiAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonnementApiRepository  extends JpaRepository<ApiAbonnement,Long> {
    public boolean existsByReferenceAndTypeAbonnement(String reference, String typeAbonnement);
}
