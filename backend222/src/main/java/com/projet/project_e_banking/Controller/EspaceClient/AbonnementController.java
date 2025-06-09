package com.projet.project_e_banking.Controller.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.AbonnementAutoDto;
import com.projet.project_e_banking.Model.EspaceClient.AbonnementAuto;
import com.projet.project_e_banking.Model.EspaceClient.User;

import com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl.CustomUserDetailsService;
import com.projet.project_e_banking.Service.EspaceClient.AbonnementService;
import com.projet.project_e_banking.Utilis.StatutFacture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/abonnement")
public class AbonnementController {

    private final AbonnementService abonnementService;
    private final CustomUserDetailsService customUserDetailsService;

    public AbonnementController(AbonnementService abonnementService,CustomUserDetailsService customUserDetailsService) {
        this.abonnementService = abonnementService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/auto")
    public ResponseEntity<?> verifierEtCreerAbonnement(@RequestBody AbonnementAutoDto abonnementAutoDto, @AuthenticationPrincipal UserDetails userDetails ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Utilisateur non connecté");
        }
        if (abonnementService.existeAbonnementByReferenceAndType(abonnementAutoDto.getReferenceClient(),abonnementAutoDto.getType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Référence client inexistante");
        }
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        AbonnementAuto abonnementAuto = new AbonnementAuto();
        abonnementAuto.setReferenceClient(abonnementAutoDto.getReferenceClient());
        abonnementAuto.setTypeAbonnement(abonnementAutoDto.getType());
        abonnementAuto.setStatut(StatutFacture.EN_ATTENTE);
        abonnementAuto.setDateProchainCheck(abonnementAutoDto.getDateProchainCheck());
        abonnementAuto.setUserId(user.getId());
        abonnementAuto.setPrix(300.0);
        abonnementAuto.setRib(abonnementAutoDto.getCompteUser());
        abonnementService.saveAbbonement(abonnementAuto);
        return ResponseEntity.ok("200");

    }

    @PostMapping("/getuser")
    public ResponseEntity<?> getuser(@RequestBody String username){
        System.out.println("hyy");
        User user=customUserDetailsService.findUserByUsername(username);
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("name", user.getName());
        response.put("phone", user.getPhone());
        response.put("address", user.getAddress());

        return ResponseEntity.ok(response);
    }
}
