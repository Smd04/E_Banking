package com.projet.project_e_banking.Repository.EspaceClient;
import com.projet.project_e_banking.Model.EspaceClient.ApiFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<ApiFacture,Long> {
    public boolean existsByReferenceAndTypeFacture(String reference, String typeFacture);
}
