package com.projet.project_e_banking.Controller.EspaceBanque;

import com.projet.project_e_banking.Dto.EspaceBanque.ClientEnrolmentRequest;
import com.projet.project_e_banking.Service.EspaceBanque.ClientEnrolmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/banque")
public class ClientEnrolmentController {

    @Autowired
    private ClientEnrolmentService clientEnrolmentService;

    @PostMapping("/enrol-client")
    public ResponseEntity<Map<String, String>> enrolClient(@RequestBody ClientEnrolmentRequest request) {
        try {
            clientEnrolmentService.enrolClient(request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Client enrôlé avec succès !");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

}