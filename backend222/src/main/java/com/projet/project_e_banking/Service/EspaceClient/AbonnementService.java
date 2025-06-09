package com.projet.project_e_banking.Service.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.AbonnementApiResponse;
import com.projet.project_e_banking.Model.EspaceClient.AbonnementAuto;

import com.projet.project_e_banking.Repository.EspaceClient.AbonnementApiRepository;
import com.projet.project_e_banking.Repository.EspaceClient.AbonnemetRepository;
import com.projet.project_e_banking.Utilis.StatutFacture;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AbonnementService {

    private final AbonnemetRepository abonnemetRepository;
    private final RestTemplate restTemplate;
    private String verificationApiUrl="http://localhost:3002/abonnements/";
    private final AbonnementApiRepository abonnementApiRepository;


    public AbonnementService(AbonnemetRepository abonnemetRepository,AbonnementApiRepository abonnementApiRepository) {
        this.abonnemetRepository=abonnemetRepository;
        this.restTemplate = new RestTemplate();
        this.abonnementApiRepository=abonnementApiRepository;
    }

    public AbonnementApiResponse verifierReferenceExiste(String reference) {
        try {
            ResponseEntity<AbonnementApiResponse> response = restTemplate.getForEntity(
                    verificationApiUrl+reference, AbonnementApiResponse.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            System.out.println("Erreur API externe : " + e.getMessage());
        }
        return null;
    }
    public void saveAbbonement(AbonnementAuto abonnementAuto){
        abonnemetRepository.save(abonnementAuto);
    }
    public boolean existeAbonnementByReferenceAndType(String reference, String type){
        return  abonnementApiRepository.existsByReferenceAndTypeAbonnement(reference, type);
    }


}
