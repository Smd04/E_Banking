package com.projet.project_e_banking.Controller.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.OtpValidationRequest;
import com.projet.project_e_banking.Dto.EspaceClient.RechargeRequest;
import com.projet.project_e_banking.Model.EspaceClient.*;

import com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl.CustomUserDetailsService;
import com.projet.project_e_banking.Service.EspaceClient.EmailService;
import com.projet.project_e_banking.Service.EspaceClient.OtpService;
import com.projet.project_e_banking.Service.EspaceClient.RechargeService;
import com.projet.project_e_banking.Service.EspaceClient.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(value = "https://localhost:4200",allowCredentials = "true")
@RequestMapping("api/recharge")
public class RechargeController {
    private final OtpService otpService;
    private final SmsService smsService;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailService emailService;
    private final RechargeService rechargeService;
    public RechargeController(CustomUserDetailsService customUserDetailsService, SmsService smsService, OtpService otpService,EmailService emailService,RechargeService rechargeService) {
        this.customUserDetailsService = customUserDetailsService;
        this.smsService = smsService;
        this.otpService = otpService;
        this.emailService=emailService;
        this.rechargeService = rechargeService;
    }


    @PostMapping("/initier")
    public ResponseEntity<String> initierRecharge(@RequestBody RechargeRequest rechargeRequest, @AuthenticationPrincipal UserDetails userDetail) {
        if (userDetail == null) {
            return ResponseEntity.status(401).body("Utilisateur non connecté");
        }
        if (rechargeRequest.getMontant() <= 0) {
            return ResponseEntity.badRequest().body("Le montant doit être positif");
        }

        User user = customUserDetailsService.findUserByUsername(userDetail.getUsername());
        if (user == null) {
            return ResponseEntity.status(401).body("Utilisateur introuvable");
        }

        Account sourceAccount = customUserDetailsService.findAccountByAccountNumber(rechargeRequest.getCompteUser());
        if (sourceAccount == null) {
            return ResponseEntity.badRequest().body("Compte source introuvable");
        }
        System.out.println(rechargeRequest.getOperatorName());

        if (rechargeRequest.getMontant() > sourceAccount.getBalance()) {
            return ResponseEntity.badRequest().body("Solde insuffisant");
        }
        if(!rechargeService.existePhoneAndOperator(rechargeRequest.getPhone(),rechargeRequest.getOperatorName())){
            return ResponseEntity.badRequest().body("Phone number invalide");
        }

        Transaction transaction = new Transaction();
        transaction.setCompteDest(rechargeRequest.getOperatorName());
        transaction.setType("Recharge");
        transaction.setAccount(sourceAccount);
        transaction.setAmount(rechargeRequest.getMontant());
        transaction.setUser(user);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("EN_ATTENTE");
        String code = String.format("%06d", new Random().nextInt(999999));
        OtpTransaction otpTransaction = new OtpTransaction();
        otpTransaction.setPhone(rechargeRequest.getPhone());
        otpTransaction.setCode(code);
        otpTransaction.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpTransaction.setTransaction(transaction);
        otpService.saveOtpTransaction(otpTransaction);
        smsService.sendOtp(user.getPhone(), code);
        emailService.envoyerEmail(user.getEmail(),"validation de Recharge",code);
        smsService.sendOtp(user.getPhone(), code);
        System.out.println(rechargeRequest.getPhone()+ "code de validation :" + code);
        return ResponseEntity.ok("Code de validation envoyé par SMS." +code);
    }

    @PostMapping("/valider")
    public ResponseEntity<?> validerRecharge(@RequestBody OtpValidationRequest request, @AuthenticationPrincipal UserDetails userDetail) {
        if (userDetail == null) {
            return ResponseEntity.status(401).body("Utilisateur non connecté");
        }
        User user = customUserDetailsService.findUserByUsername(userDetail.getUsername());
        System.out.println(request.getPhoneNumber()+request.getCode());
        Optional<OtpTransaction> optionalOtp = otpService.otpTransactionRepository
                .findByCodeAndVerifiedFalse(request.getCode());

        if (optionalOtp.isEmpty()) {
            return ResponseEntity.badRequest().body("Code OTP invalide ou déjà utilisé");
        }


        OtpTransaction otpTransaction = optionalOtp.get();

        if (otpTransaction.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Code OTP expiré");
        }

        otpTransaction.setVerified(true);
        otpService.otpTransactionRepository.save(otpTransaction);
        Transaction transaction = otpTransaction.getTransaction();
        transaction.getAccount().setBalance(transaction.getAccount().getBalance()-transaction.getAmount());
        customUserDetailsService.updateAccount(transaction.getAccount());
        Account compdest = customUserDetailsService.findAccountByAccountNumber(transaction.getCompteDest());
        int monthValue = LocalDate.now().getMonthValue();
        if (customUserDetailsService.existMonthlyBalanceByMonthAndAccountId(monthValue, transaction.getAccount().getId())) {
            MonthlyBalance monthlyBalance = customUserDetailsService.findMonthlyBalanceByMonthAndAccountId(monthValue, transaction.getAccount().getId());
            if (monthlyBalance != null) {
                monthlyBalance.setDepenses(monthlyBalance.getDepenses() + transaction.getAmount());
                customUserDetailsService.saveMonthlyBalance(monthlyBalance);
            }
        } else {
            MonthlyBalance monthlyBalance = new MonthlyBalance();
            monthlyBalance.setDepenses(transaction.getAmount());
            monthlyBalance.setAccount(transaction.getAccount());
            monthlyBalance.setRevenus(0.0);
            monthlyBalance.setMois(monthValue);
            customUserDetailsService.saveMonthlyBalance(monthlyBalance);
        }

        customUserDetailsService.updateAccount(transaction.getAccount());
        compdest.setBalance(compdest.getBalance() + transaction.getAmount());
        customUserDetailsService.updateAccount(compdest);
        if (customUserDetailsService.existMonthlyBalanceByMonthAndAccountId(monthValue, compdest.getId())) {
            MonthlyBalance monthlyBalance = customUserDetailsService.findMonthlyBalanceByMonthAndAccountId(monthValue, compdest.getId());
            if (monthlyBalance != null) {
                monthlyBalance.setRevenus(monthlyBalance.getRevenus() + transaction.getAmount());
                customUserDetailsService.saveMonthlyBalance(monthlyBalance);
            }
        } else {
            MonthlyBalance monthlyBalance = new MonthlyBalance();
            monthlyBalance.setRevenus(transaction.getAmount());
            monthlyBalance.setDepenses(0.0);
            monthlyBalance.setAccount(compdest);
            monthlyBalance.setMois(monthValue);
            customUserDetailsService.saveMonthlyBalance(monthlyBalance);
        }

        compdest.setBalance(compdest.getBalance() + transaction.getAmount());
        customUserDetailsService.updateAccount(compdest);
        transaction.setDescription("Recharge "+transaction.getAmount());
        transaction.setStatus("VALIDÉ");
        customUserDetailsService.saveTransactionTypeVirement(transaction);
        System.out.println("Recharge effectué avec succès");
        return ResponseEntity.ok("Recharge effectué avec succès");
    }



}