package com.projet.project_e_banking.Controller.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.AccountDto;
import com.projet.project_e_banking.Dto.EspaceClient.TransactionDto;
import com.projet.project_e_banking.Dto.UserDto;
import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.Card;
import com.projet.project_e_banking.Model.EspaceClient.Transaction;
import com.projet.project_e_banking.Model.EspaceClient.User;

import com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl.CustomUserDetailsService;
import com.projet.project_e_banking.Service.EspaceBanque.UserService;
import com.projet.project_e_banking.Service.EspaceClient.AccountService;
import com.projet.project_e_banking.Service.EspaceClient.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true")
@RestController
@RequestMapping("api/account")
public class AccountController {
    final
    AccountService accountService;
    UserService userService;
    TransactionService transactionService;
    CustomUserDetailsService customUserDetailsService;

    public AccountController(AccountService accountService,UserService userService ,TransactionService transactionService,CustomUserDetailsService customUserDetailsService ) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.customUserDetailsService=customUserDetailsService;
    }

    @GetMapping("/")
    public List<AccountDto> getAccounts() {
        return accountService.findAll();
    }

    @GetMapping("/account_principal")
    public ResponseEntity<?> getAccountPrincipal(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Utilisateur non connecté");
        }
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        AccountDto accountPricipal = customUserDetailsService.getAccountPrincipal(user.getId());
        System.out.println(accountPricipal.getBalance());
        return  ResponseEntity.ok(accountPricipal);


    }

    @GetMapping("/mesComptes")
    public ResponseEntity<?> getMesAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(401).body("Utilisateur non connecté");
            }
            User user = userService.findByUsername(userDetails.getUsername());
            if (user == null) {
                return ResponseEntity.status(404).body("Utilisateur non trouvé");
            }

            List<AccountDto> comptes = accountService.findByUser(user);
            System.out.println("----------------------------------------------------");
            System.out.println(comptes);
            return ResponseEntity.ok(comptes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }

    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable("id") Long id) {
        return accountService.findById(id)
                .map(accountService::toDto)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Compte introuvable pour l'id " + id
                        )
                );
    }
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable("id") Long id) {
        return accountService.getTransactionsByAccount(id);
    }
    @GetMapping("{id}/RecentTransaction")
    public ResponseEntity<?> getRecentTransactions(@PathVariable("id") Long id) {
        try{
            List<TransactionDto> transactions = transactionService.getRecentTransactions(id);
            return  ResponseEntity.ok(transactions);
        }catch (Exception e){
            return  ResponseEntity.status(500).body("Erreur interne du serveur erreur lors de chargement de recent transaction "+e.getMessage());
        }
    }

    @GetMapping("/{id}/stats")
    public Map<String, Object> getAccountStats(@PathVariable("id") Long id) {
        return accountService.getStatsByAccountId(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {
        Account account = accountService.findById(id).get();
        if (account.getBalance() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Impossible de supprimer un compte avec un solde différent de zéro.");
        }
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Compte supprimé avec succès");
    }
    @GetMapping("/{id}/MonthlyBalance")
    public ResponseEntity<?> getMonthlyBalance(@PathVariable("id") Long id) {
        try{
            Map<String,Double> monthlyBalance = accountService.getMonthlyBalanceByAccount(id);
            return ResponseEntity.ok(monthlyBalance);
        }
        catch(Exception e){
            return ResponseEntity.status(500).body("Erreur interne du serveur"+e.getMessage());
        }
    }
    @GetMapping("/get_user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(404).body("Client non connectee");
        }
        User user= customUserDetailsService.findUserByUsername(userDetails.getUsername());
        UserDto u= new UserDto();
        u.setName(user.getName());
        u.setAddress(user.getAddress());
        u.setEmail(user.getEmail());
        u.setPhone(user.getPhone());
        u.setUsername(user.getUsername());
        System.out.println(u.getAddress());
        return  ResponseEntity.ok(u);

    }

}
