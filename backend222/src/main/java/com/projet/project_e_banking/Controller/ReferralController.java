package com.projet.project_e_banking.Controller;

import com.projet.project_e_banking.Service.DaoImpl.ReferralService.ReferralService;
import com.projet.project_e_banking.Dto.ReferralRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @GetMapping("/commission/{userId}")
    public ResponseEntity<Double> getReferralCommission(@PathVariable("userId") Long userId) {
        Double commission = referralService.calculateReferralCommission(userId);
        return ResponseEntity.ok(commission);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReferral(@Valid @RequestBody ReferralRequest request) {
        try {
            referralService.addReferral(request.getReferringUserId(), request.getReferredUserId());
            return ResponseEntity.ok("Referral added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}