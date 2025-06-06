package com.projet.project_e_banking.Service.EspaceClient;

import com.projet.project_e_banking.Model.EspaceClient.OtpTransaction;

import com.projet.project_e_banking.Repository.EspaceClient.OtpTransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OtpService {
    public final OtpTransactionRepository otpTransactionRepository;
    public OtpService(OtpTransactionRepository otpTransactionRepository) {
        this.otpTransactionRepository = otpTransactionRepository;
    }
    public void saveOtpTransaction(OtpTransaction otpTransaction) {
        otpTransactionRepository.save(otpTransaction);
    }
    @Scheduled(fixedRate = 60000)
    public void nettoyerOtpsExpirés() {
        List<OtpTransaction> expirés = otpTransactionRepository.findAll()
                .stream()
                .filter(otp -> otp.getExpiryTime().isBefore(LocalDateTime.now()) && !otp.isVerified())
                .collect(Collectors.toList());

        otpTransactionRepository.deleteAll(expirés);
    }
}

