package com.projet.project_e_banking.Service.DaoImpl.ReferralService;

import com.projet.project_e_banking.Model.EspaceAdministration.SystemSettings;
import com.projet.project_e_banking.Model.ModelSystemParainage.RefferalTracking;
import com.projet.project_e_banking.Repository.RefferalTrackingRepository;
import com.projet.project_e_banking.Repository.SystemSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReferralService {

    @Autowired
    private RefferalTrackingRepository referralTrackingRepository;

    @Autowired
    private SystemSettingsRepository systemSettingsRepository;

    public Double calculateReferralCommission(Long userId) {
        SystemSettings settings = systemSettingsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User settings not found"));

        if (!settings.isReferralEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not enabled for referrals");
        }

        List<RefferalTracking> referrals = referralTrackingRepository.findByReferringUserId(userId);
        int referralCount = referrals.size();

        return referralCount * settings.getCommissionRate();
    }

    public void addReferral(Long referringUserId, Long referredUserId) {
        SystemSettings settings = systemSettingsRepository.findByUserId(referringUserId)
                .orElseThrow(() -> new RuntimeException("User settings not found"));

        if (!settings.isReferralEnabled()) {
            throw new IllegalStateException("Referral system is not enabled for this user");
        }

        RefferalTracking referral = new RefferalTracking();
        referral.setReferringUserId(referringUserId);
        referral.setReferredUserId(referredUserId);
        referralTrackingRepository.save(referral);
    }
}