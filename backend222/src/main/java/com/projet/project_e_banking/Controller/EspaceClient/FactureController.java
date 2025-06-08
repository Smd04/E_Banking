package com.projet.project_e_banking.Controller.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.FactureAutoDto;
import com.projet.project_e_banking.Model.EspaceClient.FactureAuto;
import com.projet.project_e_banking.Model.EspaceClient.User;

import com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl.CustomUserDetailsService;
import com.projet.project_e_banking.Service.EspaceClient.FactureService;
import com.projet.project_e_banking.Utilis.StatutFacture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    private final FactureService factureService;
    private final CustomUserDetailsService customUserDetailsService;

    public FactureController(FactureService factureService,CustomUserDetailsService customUserDetailsService) {
        this.factureService = factureService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/auto")
    public ResponseEntity<?> verifierEtCreerFacture(@RequestBody FactureAutoDto factureAutoDto, @AuthenticationPrincipal UserDetails userDetails ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Utilisateur non connecté");
        }
        if (!factureService.existeFactureByReferenceAndType(factureAutoDto.getReferenceClient(),factureAutoDto.getType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Référence client inexistante");
        }
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        FactureAuto factureAuto = new FactureAuto();
        factureAuto.setReferenceClient(factureAutoDto.getReferenceClient());
        factureAuto.setTypeFacture(factureAutoDto.getType());
        factureAuto.setStatut(StatutFacture.EN_ATTENTE);
        factureAuto.setDateProchainCheck(factureAutoDto.getDateProchainCheck());
        factureAuto.setUserId(user.getId());
        factureAuto.setPrix(100.0);
        factureAuto.setRib(factureAutoDto.getCompteUser());
        factureService.saveFacture(factureAuto);
        return ResponseEntity.ok("200");

    }
}
