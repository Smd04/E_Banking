package com.projet.project_e_banking.Repository.BanqueRepository;

import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    Account findAccountByUserIdAndType(Long userId, String accountType);
    List<Account> findByUser(User user);
    List<Account>findByUserUsername(String username);
    Account findAccountByAccountNumber(String accountNumber);

}
