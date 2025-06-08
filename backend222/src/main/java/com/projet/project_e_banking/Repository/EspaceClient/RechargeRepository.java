package com.projet.project_e_banking.Repository.EspaceClient;


import com.projet.project_e_banking.Model.EspaceClient.ApiRecharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RechargeRepository extends JpaRepository<ApiRecharge,Long> {
    public boolean existByPhoneAndOperateur(String phone, String operateur);
}