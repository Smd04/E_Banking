package com.projet.project_e_banking.Controller.EspaceClient;

import com.projet.project_e_banking.Dto.AdministrationDto.SupportMessageDto;
import com.projet.project_e_banking.Dto.EspaceClient.SupportRequest;
import com.projet.project_e_banking.Model.EspaceAdministration.Administrator;
import com.projet.project_e_banking.Model.EspaceAdministration.SupportMessage;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Service.DaoImpl.AdministrationDaoImpl.SupportMessageDaoImpl;
import com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@CrossOrigin(value = "http://localhost:4200",allowCredentials = "true")
@RequestMapping("/api/support")
public class SupportController {
    private final CustomUserDetailsService customUserDetailsService;
    public SupportController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    @PostMapping("/send")
    public ResponseEntity<?> supportMessage( @RequestBody SupportRequest supportRequest,@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails == null){
            return ResponseEntity.status(404).body("User non connectee");
        }
        System.out.println(supportRequest.getMessage());
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        Administrator administrator = new Administrator();
        SupportMessage message = new SupportMessage();
        message.setMessage(supportRequest.getMessage());
        message.setType(supportRequest.getTypeSupport());
        message.setUser(user);
        message.setTimestamp(new Date());
        message.setAdmin(null);
        customUserDetailsService.sendMessage(message);
        return ResponseEntity.ok(message);


    }


}