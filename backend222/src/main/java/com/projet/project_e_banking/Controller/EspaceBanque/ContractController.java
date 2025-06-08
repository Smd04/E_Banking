package com.projet.project_e_banking.Controller.EspaceBanque;

import com.projet.project_e_banking.Dto.EspaceBanque.ContractRequest;
import com.projet.project_e_banking.Service.EspaceBanque.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/banque")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/create-contract")
    public ResponseEntity<Map<String, String>> createContract(@RequestBody ContractRequest request) {
        try {
            contractService.createContract(request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Contrat créé avec succès !");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}