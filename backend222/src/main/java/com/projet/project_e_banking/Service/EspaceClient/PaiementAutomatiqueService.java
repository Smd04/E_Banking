package com.projet.project_e_banking.Service.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.AbonnementAuto;
import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.FactureAuto;
import com.projet.project_e_banking.Model.EspaceClient.Paiement;

import com.projet.project_e_banking.Repository.BanqueRepository.AccountRepository;
import com.projet.project_e_banking.Repository.EspaceClient.AbonnemetRepository;
import com.projet.project_e_banking.Repository.EspaceClient.FactureAutoRepository;
import com.projet.project_e_banking.Repository.EspaceClient.PaiementRepository;
import com.projet.project_e_banking.Utilis.StatutFacture;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PaiementAutomatiqueService {
    @Autowired
    private FactureAutoRepository factureRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaiementRepository paiementRepository;
    @Autowired
    private AbonnemetRepository abonnemetRepository;

    //private NotificationService notificationService;

    @Scheduled(cron = "0 0 22 * * ?")
    public void verifierEtPayerFactures() {
        List<FactureAuto> factures = factureRepository.findByStatut(StatutFacture.EN_ATTENTE);
        for (FactureAuto f : factures) {
            if(f.getDateProchainCheck().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
            double montant = f.getPrix();
            Account compte = accountRepository.findByAccountNumber(f.getRib()).orElse(null);

            if (compte.getBalance() >= montant) {
                compte.setBalance(compte.getBalance() - montant);
                Paiement paiement= new Paiement();
                paiement.setCompteDebite(f.getRib());
                paiement.setDatePaiement(LocalDateTime.now());
                paiement.setMontant(montant);
                paiement.setStatut(StatutFacture.FAITE);
                paiement.setReferenceFacture(f.getReferenceClient());
                paiement.setType(f.getTypeFacture());
                paiementRepository.save(paiement);
                f.setDateProchainCheck(LocalDateTime.now().plusMonths(1));
                factureRepository.save(f);
               // notificationService.envoyer(f.getUserId(), "Facture payée automatiquement : " + montant + " MAD");
            } else {
                //notificationService.envoyer(f.getUserId(), "Paiement automatique échoué : solde insuffisant.");
                System.out.println("Erreur de verification");
            }}else{
                System.out.println("Erreur de verification");
            }
        }
    }
    @Scheduled(cron = "0 0 22 * * ?")
    public void verifierEtPayerAbonnements() {
        List<AbonnementAuto> abonnements = abonnemetRepository.findByStatut(StatutFacture.EN_ATTENTE);
        for (AbonnementAuto f : abonnements) {
            if(f.getDateProchainCheck().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) {
                double montant = f.getPrix();
                Account compte = accountRepository.findByAccountNumber(f.getRib()).orElse(null);

                if (compte.getBalance() >= montant) {
                    compte.setBalance(compte.getBalance() - montant);
                    Paiement paiement= new Paiement();
                    paiement.setCompteDebite(f.getRib());
                    paiement.setDatePaiement(LocalDateTime.now());
                    paiement.setMontant(montant);
                    paiement.setStatut(StatutFacture.FAITE);
                    paiement.setReferenceFacture(f.getReferenceClient());
                    paiement.setType(f.getTypeAbonnement());
                    paiementRepository.save(paiement);
                    f.setDateProchainCheck(LocalDateTime.now().plusMonths(1));
                    abonnemetRepository.save(f);
                    // notificationService.envoyer(f.getUserId(), "Facture payée automatiquement : " + montant + " MAD");
                } else {
                    //notificationService.envoyer(f.getUserId(), "Paiement automatique échoué : solde insuffisant.");
                    System.out.println("Erreur de verification");
                }}else{
                System.out.println("Erreur de verification");
            }
        }
    }
}
