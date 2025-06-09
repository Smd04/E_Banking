package com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl;


import com.projet.project_e_banking.Dto.EspaceClient.AccountDto;
import com.projet.project_e_banking.Dto.EspaceClient.MonthlyBalanceDto;
import com.projet.project_e_banking.Dto.EspaceClient.TransactionDto;
import com.projet.project_e_banking.Model.EspaceAdministration.Beneficiary;
import com.projet.project_e_banking.Model.EspaceAdministration.SupportMessage;
import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.MonthlyBalance;
import com.projet.project_e_banking.Model.EspaceClient.Transaction;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.AdministarationRepository.SupportMessagesRepository;
import com.projet.project_e_banking.Repository.BanqueRepository.AccountRepository;
import com.projet.project_e_banking.Repository.EspaceClient.BeneficiaryRepository;
import com.projet.project_e_banking.Repository.EspaceClient.MonthlyBalanceRepository;
import com.projet.project_e_banking.Repository.EspaceClient.TransactionRepository;
import com.projet.project_e_banking.Repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private final BeneficiaryRepository beneficiaryRepository;
    private final MonthlyBalanceRepository monthlyBalanceRepository;
    private final SupportMessagesRepository supportMessagesRepository;


    public CustomUserDetailsService(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, BeneficiaryRepository beneficiaryRepository, MonthlyBalanceRepository monthlyBalanceRepository, SupportMessagesRepository supportMessagesRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;

        this.beneficiaryRepository = beneficiaryRepository;
        this.monthlyBalanceRepository = monthlyBalanceRepository;

        this.supportMessagesRepository = supportMessagesRepository;
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

    public List<Account> userAccounts(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getAccounts();
    }

    public User findUserByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        User user = account.getUser();
        return user;

    }

    public Account findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }

    public String saveTransactionTypeVirement(Transaction transaction) {
        transactionRepository.save(transaction);
        return "transaction";
    }

    public void updateAccount(Account account) {
        accountRepository.save(account);
    }



    public Account findAccountIdUser(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }




    public boolean isBeneficiaryExist(String accountNumber) {
        return beneficiaryRepository.existsBeneficiaryByAccountNumber(accountNumber);
    }

    public void save(Beneficiary beneficiary) {
        beneficiaryRepository.save(beneficiary);
    }
    public void saveMonthlyBalance(MonthlyBalance monthlyBalance){
        monthlyBalanceRepository.save(monthlyBalance);
    }

    public void updateMonthlyBalance(MonthlyBalance monthlyBalance){
        monthlyBalanceRepository.save(monthlyBalance);
    }

    public MonthlyBalance findMonthlyBalanceByMonthAndAccountId(int month,Long id) {
        return monthlyBalanceRepository.findByMoisAndAccount_Id(month,id);
    }

    public boolean existMonthlyBalanceByMonthAndAccountId(int monthValue, Long id) {
        return monthlyBalanceRepository.existsByMoisAndAccountId(monthValue,id);

    }

    public MonthlyBalanceDto findMonthBalancyBymonth(int mois, Long accountId) {
        MonthlyBalance monthlyBalance = monthlyBalanceRepository.findByMoisAndAccount_Id(mois, accountId);
        MonthlyBalanceDto dto = new MonthlyBalanceDto();
        dto.setDepenses(monthlyBalance.getDepenses());
        dto.setRevenues(monthlyBalance.getRevenus());
        return dto;
    }
    public List<TransactionDto> getRecentTransaction(Long acccount_id){
        List<Transaction> transactions= transactionRepository.findTop5ByAccountIdOrderByTimestampDesc(acccount_id);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for(Transaction t :transactions){
            TransactionDto dto = new TransactionDto();
            dto.setCompteDest(t.getCompteDest());
            dto.setAmount(t.getAmount());
            dto.setStatus(t.getStatus());
            dto.setTimestamp(t.getTimestamp());
            dto.setDescription(t.getDescription());
            dto.setAccountId(t.getAccount().getId());
            transactionDtoList.add(dto);
        }
        return transactionDtoList;

    }
    public void sendMessage(SupportMessage supportMessage){
        supportMessagesRepository.save(supportMessage);

    }}