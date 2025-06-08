package com.projet.project_e_banking.Service.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.AccountDto;
import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.Card;
import com.projet.project_e_banking.Model.EspaceClient.Transaction;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.BanqueRepository.AccountRepository;
import com.projet.project_e_banking.Repository.EspaceClient.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;


    public List<Account> findByUsername(String username) {
        return accountRepository.findByUserUsername(username);
    }

    public List<AccountDto> findAll() {
        List<AccountDto> dtos = accountRepository.findAll().stream().map(account -> {
            AccountDto dto = new AccountDto();
            dto.setId(account.getId());
            dto.setType(account.getType());
            dto.setBalance(account.getBalance());
            dto.setCurrency(account.getCurrency());

            dto.setStatus(account.getStatus());
            dto.setAccountNumber(account.getAccountNumber());
            return dto;
        }).collect(Collectors.toList());

        Collections.reverse(dtos);
        return dtos;
    }
    public List<AccountDto> findByUser(User user) {
        List<AccountDto> dtos = accountRepository.findByUser(user).stream().map(account -> {
            AccountDto dto = new AccountDto();
            dto.setId(account.getId());
            dto.setType(account.getType());
            dto.setBalance(account.getBalance());
            dto.setCurrency(account.getCurrency());
            dto.setStatus(account.getStatus());
            dto.setAccountNumber(account.getAccountNumber());
            return dto;
        }).collect(Collectors.toList());

        Collections.reverse(dtos);
        return dtos;
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }
    public List<Transaction> getTransactionsByAccount(Long id) {
        Account account = accountRepository.findById(id).get();
        return account != null ? account.getTransactions() : List.of();
    }

    public Map<String,Object>getStatsByAccountId(Long id) {
        Account account = accountRepository.findById(id).get();
        if(account == null ) return Map.of();
        double totalDepenses = account.getTransactions().stream()
                .filter(t ->t.getType().equalsIgnoreCase("débit"))
                .mapToDouble(Transaction::getAmount).sum();
        return Map.of(
                "solde" , account.getBalance(),
                "devise",account.getCurrency(),
                "depensesTotales",totalDepenses,
                "nombreTransactions", account.getTransactions().size()
        );
    }
    public AccountDto toDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setType(account.getType());
        accountDto.setBalance(account.getBalance());
        accountDto.setCurrency(account.getCurrency());
        accountDto.setStatus(account.getStatus());
        accountDto.setAccountNumber(account.getAccountNumber());
        return accountDto;
    }
    public void deleteAccount(Long id) {

        accountRepository.deleteById(id);
    }

    public Map<String,Double> getMonthlyBalanceByAccount(Long id ) {
        List<Transaction> transactions = transactionRepository.findByAccountId(id);
        Map<String,Double> monthlyBalance = new LinkedHashMap<>();
        YearMonth now = YearMonth.now();
        //Initialiser
        for(int i =4 ;i>=0;i--) {
            YearMonth month = now.minusMonths(i);
            String monthName = month.getMonth().getDisplayName(TextStyle.FULL,Locale.FRENCH);
            monthlyBalance.put(monthName,0.0);
        }
        if(transactions == null|| transactions.isEmpty()) {
            return monthlyBalance;
        }
        transactions.sort(Comparator.comparing(Transaction::getTimestamp));
        //Regrouper les transaction par mois
        Map<YearMonth,List<Transaction>> transactionsByMonth = transactions.stream()
                .collect(Collectors.groupingBy(t -> YearMonth.from(t.getTimestamp())));
        double runningBalance =accountRepository.findById(id).get().getBalance();
        for(int i=0;i<=4;i++){
            YearMonth month = now.minusMonths(i);
            List<Transaction>monthlyTx = transactionsByMonth.getOrDefault(month, new ArrayList<>());
            for(Transaction t : monthlyTx) {
                String type = t.getType().toLowerCase();
                if (type.equals("virement") || type.equals("recharge") || type.equals("paiment")) {
                    runningBalance += t.getAmount();
                }else{
                    runningBalance -= t.getAmount();
                }
            }
            String monthName = month.getMonth().getDisplayName(TextStyle.FULL,Locale.FRENCH);
            monthlyBalance.put(monthName,runningBalance);
        }
        return monthlyBalance;
    }
}

