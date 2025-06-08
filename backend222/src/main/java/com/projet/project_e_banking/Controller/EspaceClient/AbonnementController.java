package com.projet.project_e_banking.Controller.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.AbonnementApiResponse;
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
        if (abonnementService.verifierReferenceExiste(abonnementAutoDto.getReferenceClient()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Référence client inexistante");
        }
        AbonnementApiResponse abonnementApiResponse = abonnementService.verifierReferenceExiste(abonnementAutoDto.getReferenceClient());
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        AbonnementAuto abonnementAuto = new AbonnementAuto();
        abonnementAuto.setReferenceClient(abonnementAutoDto.getReferenceClient());
        abonnementAuto.setTypeAbonnement(abonnementAutoDto.getType());
        abonnementAuto.setStatut(StatutFacture.EN_ATTENTE);
        abonnementAuto.setDateProchainCheck(abonnementAutoDto.getDateProchainCheck());
        abonnementAuto.setUserId(user.getId());
        abonnementAuto.setPrix(abonnementApiResponse.getMontant());
        abonnementAuto.setRib(abonnementAutoDto.getCompteUser());
        abonnementService.saveAbbonement(abonnementAuto);
        return ResponseEntity.ok("200");

    }
}

