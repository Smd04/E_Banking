package com.projet.project_e_banking.Service.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.FactureApiResponse;
import com.projet.project_e_banking.Model.EspaceClient.FactureAuto;

import com.projet.project_e_banking.Repository.EspaceClient.FactureAutoRepository;
import com.projet.project_e_banking.Repository.EspaceClient.FactureRepository;
import com.projet.project_e_banking.Utilis.StatutFacture;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FactureService {

    private final FactureAutoRepository factureAutoRepository;
    private final RestTemplate restTemplate;
    private String verificationApiUrl="http://localhost:3001/factures/";
    private final FactureRepository factureRepository;


    public FactureService(FactureAutoRepository factureAutoRepository, FactureRepository factureRepository) {
        this.factureAutoRepository = factureAutoRepository;
        this.restTemplate = new RestTemplate();
        this.factureRepository = factureRepository;
    }

    public FactureApiResponse verifierReferenceExiste(String reference) {
        try {
            ResponseEntity<FactureApiResponse> response = restTemplate.getForEntity(
                    verificationApiUrl+reference, FactureApiResponse.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            System.out.println("Erreur API externe : " + e.getMessage());
        }
        return null;
    }
    public void saveFacture(FactureAuto factureAuto){
        factureAutoRepository.save(factureAuto);
    }
    public List<FactureAuto> getFactureEnAttente(){
        return factureAutoRepository.findByStatut(StatutFacture.EN_ATTENTE);
    }
    public boolean existeFactureByReferenceAndType(String reference, String type){
        return  factureRepository.existsByReferenceAndTypeFacture(reference,type);
    }

}

