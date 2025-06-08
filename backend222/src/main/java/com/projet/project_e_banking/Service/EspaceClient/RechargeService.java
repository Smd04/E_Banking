package com.projet.project_e_banking.Service.EspaceClient;


import com.projet.project_e_banking.Repository.EspaceClient.RechargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargeService {
    @Autowired
    private RechargeRepository recRepo;
    public boolean existePhoneAndOperator(String phone, String operator) {
        return recRepo.existsByPhoneAndOperateur(phone,operator);

    }
}