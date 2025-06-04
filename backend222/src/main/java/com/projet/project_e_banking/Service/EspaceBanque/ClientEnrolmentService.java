package com.projet.project_e_banking.Service.EspaceBanque;

import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Model.EspaceBanque.Bank;
import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Dto.EspaceBanque.ClientEnrolmentRequest;
import com.projet.project_e_banking.Repository.BanqueRepository.AccountRepository;
import com.projet.project_e_banking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientEnrolmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void enrolClient(ClientEnrolmentRequest request) {
        // Vérifie si l'utilisateur existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        // Création du User
        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(request.getStatus());
        user.setCity(request.getCity());
        user.setAddress(request.getAddress());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);

        // Création du compte
        Account account = new Account();
        account.setType(request.getType());
        account.setBalance(request.getInitialDeposit());
        account.setCurrency(request.getCurrency());
        account.setStatus(request.getStatus()); // ou "en attente"
        account.setUser(user);

        accountRepository.save(account);
    }
}

