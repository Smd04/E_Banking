package com.projet.project_e_banking.Controller.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.AccountDto;
import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.Paiement;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl.CustomUserDetailsService;
import com.projet.project_e_banking.Service.EspaceClient.PaiementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/paiement")
public class PaiementController {
    private PaiementService paiementService;
    private CustomUserDetailsService customUserDetailsService;
    public PaiementController(PaiementService paiementService, CustomUserDetailsService customUserDetailsService) {
        this.paiementService = paiementService;
        this.customUserDetailsService = customUserDetailsService;
    }
    @GetMapping("/recent_paiement")
    public ResponseEntity<?> getRecentPaiement(@AuthenticationPrincipal UserDetails userDetails) {
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        AccountDto account = customUserDetailsService.getAccountPrincipal(user.getId());
        List<Paiement> paiements = paiementService.getRecentPaiement(account.getAccountNumber());
        return ResponseEntity.ok(paiements);
    }
    @GetMapping("/all_paiement")
    public ResponseEntity<?> getAllPaiement(@AuthenticationPrincipal UserDetails userDetails) {
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        AccountDto account = customUserDetailsService.getAccountPrincipal(user.getId());
        List<Paiement> paiements=paiementService.getAllPaiement(account.getAccountNumber());
        return ResponseEntity.ok(paiements);
    }

}
