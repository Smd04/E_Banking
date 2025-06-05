package com.projet.project_e_banking.Controller.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.TransactionDto;
import com.projet.project_e_banking.Model.EspaceClient.Transaction;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Service.EspaceBanque.UserService;
import com.projet.project_e_banking.Service.EspaceClient.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/transaction/")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @GetMapping("me")
    public ResponseEntity<?> getMyTransaction(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(401).body("Utilisateur non connecté");
            }

            User user = userService.findByUsername(userDetails.getUsername());
            if (user == null) {
                return ResponseEntity.status(404).body("Utilisateur non trouvé");
            }

            List<TransactionDto> transactions = transactionService.getTransactionByUserId(user.getId());
            System.out.println("------------------------------------------------");
            System.out.println("------------------------------------------------");
            System.out.println("transaction"+transactions.get(0));
            System.out.println("------------------------------------------------");
            System.out.println("------------------------------------------------");

            return ResponseEntity.ok(transactions);

        } catch (Exception e) {

           System.out.println("error lors de récuparation des transactions");
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }

    @GetMapping("by-compte/{idCompte}")
    public ResponseEntity<?> getByCompte(@PathVariable("idCompte") Long idCompte) {
        try{
            List<TransactionDto> transactions = transactionService.getTransactionByUserId(idCompte);
            return ResponseEntity.ok(transactions);
        }catch (Exception e) {

            System.out.println("error lors de récuparation des transactions de compte");
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }
@GetMapping("recentTransaction/{idCompte}")
    public ResponseEntity<?> getRecentTransaction(@PathVariable("idCompte") Long idCompte) {
    LocalDate today = LocalDate.now();
    LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
    LocalDateTime startDateTime = startOfWeek.atStartOfDay();
    Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
    List<TransactionDto> transactions = transactionService.getTransactionByUserId(idCompte);
    try{
        return ResponseEntity.ok(transactions);
    }catch (Exception e) {

        System.out.println("error lors de récuparation de RECENT transactions ");
        return ResponseEntity.status(500).body("Erreur interne du serveur");
    }
}


}
