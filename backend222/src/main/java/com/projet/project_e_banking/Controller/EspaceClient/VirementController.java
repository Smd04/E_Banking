package com.projet.project_e_banking.Controller.EspaceClient;

import com.projet.project_e_banking.Dto.EspaceClient.OtpValidationRequest;
import com.projet.project_e_banking.Dto.EspaceClient.VirementRequest;
import com.projet.project_e_banking.Model.EspaceAdministration.Beneficiary;
import com.projet.project_e_banking.Model.EspaceClient.*;


import com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl.CustomUserDetailsService;
import com.projet.project_e_banking.Service.EspaceClient.EmailService;
import com.projet.project_e_banking.Service.EspaceClient.OtpService;
import com.projet.project_e_banking.Service.EspaceClient.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("api/virement")
public class VirementController {

    private final Map<String, OtpTransaction> otpTransactionStorage = new ConcurrentHashMap<>();
    private final OtpService otpService;


    private final SmsService smsService;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailService emailService;

    public VirementController(CustomUserDetailsService customUserDetailsService, SmsService smsService, OtpService otpService,EmailService emailService) {
        this.customUserDetailsService = customUserDetailsService;
        this.smsService = smsService;
        this.otpService = otpService;
        this.emailService=emailService;
    }

    @PostMapping("/initier")
    public ResponseEntity<String> initierVirement(@RequestBody VirementRequest virementRequest, @AuthenticationPrincipal UserDetails userDetail) {
        if (userDetail == null) {
            return ResponseEntity.status(401).body("Utilisateur non connecté");
        }
        if (virementRequest.getMontant() <= 0) {
            return ResponseEntity.badRequest().body("Le montant doit être positif");
        }

        User user = customUserDetailsService.findUserByUsername(userDetail.getUsername());
        if (user == null) {
            return ResponseEntity.status(401).body("Utilisateur introuvable");
        }

        Account sourceAccount = customUserDetailsService.findAccountByAccountNumber(virementRequest.getCompteUser());
        if (sourceAccount == null) {
            return ResponseEntity.badRequest().body("Compte source introuvable");
        }

        Account destAccount = customUserDetailsService.findAccountByAccountNumber(virementRequest.getCompteDestinataire());
        if (destAccount == null) {
            return ResponseEntity.badRequest().body("Compte destinataire introuvable");
        }

        if (virementRequest.getMontant() > sourceAccount.getBalance()) {
            return ResponseEntity.badRequest().body("Solde insuffisant");
        }

        Transaction transaction = new Transaction();
        transaction.setCompteDest(destAccount.getAccountNumber());
        transaction.setType("VIREMENT");
        transaction.setAccount(sourceAccount);
        transaction.setAmount(virementRequest.getMontant());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("EN_ATTENTE");
        transaction.setUser(user);
        transaction.setDescription(virementRequest.getMotif());

        String code = String.format("%06d", new Random().nextInt(999999));
        OtpTransaction otpTransaction = new OtpTransaction();
        otpTransaction.setPhone(user.getPhone());
        otpTransaction.setCode(code);
        otpTransaction.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpTransaction.setTransaction(transaction);
        otpService.saveOtpTransaction(otpTransaction);

        smsService.sendOtp(user.getPhone(), code);
       emailService.envoyerEmail(user.getEmail(),"validation de virement",code);
        System.out.println(virementRequest.getPhoneNumber()+ "code de validation :" + code);

        return ResponseEntity.ok("Code de validation envoyé par SMS: "+code);
    }

    @PostMapping("/valider")
    public ResponseEntity<?> validerVirement(@RequestBody OtpValidationRequest request, @AuthenticationPrincipal UserDetails userDetail) {
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
        Account comptedest = customUserDetailsService.findAccountByAccountNumber(transaction.getCompteDest());
        if(!customUserDetailsService.isBeneficiaryExist(comptedest.getAccountNumber())){
            Beneficiary beneficiary=new Beneficiary();
            beneficiary.setUser(user);
            beneficiary.setAccountNumber(comptedest.getAccountNumber());
            customUserDetailsService.save(beneficiary);

        }
        Account sourceAccount = customUserDetailsService.findAccountByAccountNumber(transaction.getAccount().getAccountNumber());
        sourceAccount.setBalance(sourceAccount.getBalance() - transaction.getAmount());
        int monthValue = LocalDate.now().getMonthValue();
        if (customUserDetailsService.existMonthlyBalanceByMonthAndAccountId(monthValue, sourceAccount.getId())) {
            MonthlyBalance monthlyBalance = customUserDetailsService.findMonthlyBalanceByMonthAndAccountId(monthValue, sourceAccount.getId());
            if (monthlyBalance != null) {
                monthlyBalance.setDepenses(monthlyBalance.getDepenses() + transaction.getAmount());
                customUserDetailsService.saveMonthlyBalance(monthlyBalance);
            }
        } else {
            MonthlyBalance monthlyBalance = new MonthlyBalance();
            monthlyBalance.setDepenses(transaction.getAmount());
            monthlyBalance.setAccount(sourceAccount);
            monthlyBalance.setRevenus(0.0);
            monthlyBalance.setMois(monthValue);
            customUserDetailsService.saveMonthlyBalance(monthlyBalance);
        }

        customUserDetailsService.updateAccount(sourceAccount);
        comptedest.setBalance(comptedest.getBalance() + transaction.getAmount());
        customUserDetailsService.updateAccount(comptedest);
        if (customUserDetailsService.existMonthlyBalanceByMonthAndAccountId(monthValue, comptedest.getId())) {
            MonthlyBalance monthlyBalance = customUserDetailsService.findMonthlyBalanceByMonthAndAccountId(monthValue, comptedest.getId());
            if (monthlyBalance != null) {
                monthlyBalance.setRevenus(monthlyBalance.getRevenus() + transaction.getAmount());
                customUserDetailsService.saveMonthlyBalance(monthlyBalance);
            }
        } else {
            MonthlyBalance monthlyBalance = new MonthlyBalance();
            monthlyBalance.setRevenus(transaction.getAmount());
            monthlyBalance.setDepenses(0.0);
            monthlyBalance.setAccount(comptedest);
            monthlyBalance.setMois(monthValue);
            customUserDetailsService.saveMonthlyBalance(monthlyBalance);
        }

        transaction.setStatus("VALIDÉ");
        customUserDetailsService.saveTransactionTypeVirement(transaction);
        System.out.println("Virement effectué avec succès");
        return ResponseEntity.ok("Virement effectué avec succès");
    }
}
