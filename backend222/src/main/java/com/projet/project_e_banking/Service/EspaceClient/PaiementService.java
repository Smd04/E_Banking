package com.projet.project_e_banking.Service.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.Paiement;

import com.projet.project_e_banking.Repository.EspaceClient.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaiementService {
    @Autowired
    private PaiementRepository paiementRepository;
    public List<Paiement> getAllPaiement(String compteDebite){
        return paiementRepository.findAllByCompteDebite(compteDebite);
    }
    public List<Paiement> getRecentPaiement(String compteDebite){
        return paiementRepository.findTop5ByCompteDebiteOrderByDatePaiementDesc(compteDebite);
    }


}
