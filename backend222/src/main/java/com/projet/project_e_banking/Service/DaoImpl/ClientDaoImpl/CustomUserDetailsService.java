package com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl;


import com.projet.project_e_banking.Dto.EspaceClient.AccountDto;
import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.BanqueRepository.AccountRepository;
import com.projet.project_e_banking.Repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository ;
    private final AccountRepository accountRepository ;

    public CustomUserDetailsService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user: " + username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("Found user: " + user.getUsername());
        System.out.println("Stored password: " + user.getPassword());
        System.out.println("Roles: " + user.getRole());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                )
                .build();
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public AccountDto getAccountPrincipal(Long userId){
        Account account =accountRepository.findAccountByUserIdAndType(userId,"COURANT");
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setType(account.getType());
        dto.setBalance(account.getBalance());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setCurrency(account.getCurrency());
        dto.setStatus(account.getStatus());
        dto.setAccountId(account.getId());
        return dto;
    }



}