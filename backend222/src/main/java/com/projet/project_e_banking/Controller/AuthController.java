package com.projet.project_e_banking.Controller;


import com.projet.project_e_banking.Config.JwtTokenProvider;
import com.projet.project_e_banking.Dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try{
            System.out.println("Raw password received: " + loginRequest.getPassword());
            System.out.println("Login attempt for user: " + loginRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", jwt);
            return ResponseEntity.ok(response);
        } catch (
                AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw e;
        }
    }

    @GetMapping("/login")
    public String authenticateUser2(){
        return "auth workks ";
    }
}
