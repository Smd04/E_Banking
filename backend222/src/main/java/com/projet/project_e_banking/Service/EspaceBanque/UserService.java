package com.projet.project_e_banking.Service.EspaceBanque;

import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


}
