package com.projet.project_e_banking.Repository;


import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Utilis.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);

}