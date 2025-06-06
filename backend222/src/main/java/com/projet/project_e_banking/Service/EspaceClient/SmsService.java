package com.projet.project_e_banking.Service.EspaceClient;


import com.projet.project_e_banking.Model.EspaceClient.SmsSent;

import com.projet.project_e_banking.Repository.EspaceClient.SmsSentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SmsService {

    private final SmsSentRepository smsSentRepository;

    @Autowired
    public SmsService(SmsSentRepository smsSentRepository) {
        this.smsSentRepository = smsSentRepository;
    }

    public void sendOtp(String phone, String code) {
        String message = "Votre code OTP est : " + code + " (valide 5 minutes)";

        SmsSent sms = new SmsSent();
        sms.setPhone(phone);
        sms.setMessage(message);
        sms.setSentAt(LocalDateTime.now());

        smsSentRepository.save(sms);

        System.out.println("[SIMULATION SMS] => To: " + phone + " | Message: " + message);
    }
}

