package com.projet.project_e_banking.Controller.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.AccountDto;
import com.projet.project_e_banking.Dto.EspaceClient.MonthlyBalanceDto;
import com.projet.project_e_banking.Dto.EspaceClient.TransactionDto;
import com.projet.project_e_banking.Model.EspaceClient.Account;
import com.projet.project_e_banking.Model.EspaceClient.MonthlyBalance;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Service.DaoImpl.ClientDaoImpl.CustomUserDetailsService;
import com.projet.project_e_banking.Service.EspaceClient.AccountService;
import com.projet.project_e_banking.Service.EspaceClient.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/dashboard")
public class DashboardController {
    AccountService accountService;
    CustomUserDetailsService customUserDetailsService;
    TransactionService transactionService;

    public DashboardController(AccountService accountService, CustomUserDetailsService customUserDetailsService , TransactionService transactionService ) {
        this.accountService = accountService;
        this.customUserDetailsService = customUserDetailsService;
        this.transactionService = transactionService;
    }
    @GetMapping("/account_principal")
    public ResponseEntity<?> getAccountPrincipal(@AuthenticationPrincipal UserDetails userDetails) {
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        AccountDto accountPricipal = customUserDetailsService.getAccountPrincipal(user.getId());
        System.out.println(accountPricipal.getBalance());
        return  ResponseEntity.ok(accountPricipal);


    }
    @GetMapping("/monthly_balance")
    public ResponseEntity<?> getMonthlyBalance(@AuthenticationPrincipal UserDetails userDetails){
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        AccountDto accountPricipal = customUserDetailsService.getAccountPrincipal(user.getId());
        int monthValue = LocalDate.now().getMonthValue();
        if(!customUserDetailsService.existMonthlyBalanceByMonthAndAccountId(monthValue,accountPricipal.getId())){
            MonthlyBalanceDto monthlyBalanceDto=new MonthlyBalanceDto();
            monthlyBalanceDto.setDepenses(0.0);
            monthlyBalanceDto.setRevenues(0.0);
            return ResponseEntity.ok(monthlyBalanceDto);

        }
        MonthlyBalanceDto monthlyBalanceDto = customUserDetailsService.findMonthBalancyBymonth(monthValue,accountPricipal.getId());
        return  ResponseEntity.ok(monthlyBalanceDto);
    }
    @GetMapping("/recent_transactions")
    public ResponseEntity<?> getFiveTransactions(@AuthenticationPrincipal UserDetails userDetails){
        User user = customUserDetailsService.findUserByUsername(userDetails.getUsername());
        AccountDto accountPricipal = customUserDetailsService.getAccountPrincipal(user.getId());
        List<TransactionDto> transactionDtoList = customUserDetailsService.getRecentTransaction(accountPricipal.getId());
        return ResponseEntity.ok(transactionDtoList);

    }
    @GetMapping("/activiter_compte")
    public ResponseEntity getMonthlyAccountActivity(@AuthenticationPrincipal UserDetails userDetails) {
        User user=customUserDetailsService.findUserByUsername(userDetails.getUsername());
        AccountDto accountPricipal = customUserDetailsService.getAccountPrincipal(user.getId());
        Map<String, Double> activityByMonth = new LinkedHashMap<>();
        int monthValue = LocalDate.now().getMonthValue();
        double runningBalance = accountPricipal.getBalance();

        for(int i=0;i<=4;i++){
            double depenses = 0;
            double revenus = 0;
            if(customUserDetailsService.existMonthlyBalanceByMonthAndAccountId(monthValue-i,accountPricipal.getId())){
                MonthlyBalanceDto dto = customUserDetailsService.findMonthBalancyBymonth(monthValue-i,accountPricipal.getAccountId());
                depenses = dto.getDepenses();
                revenus = dto.getRevenues();
            }
                activityByMonth.put(Month.of(monthValue-i).getDisplayName(TextStyle.FULL, Locale.ENGLISH),runningBalance);
            runningBalance += depenses - revenus;


        }



       return ResponseEntity.ok(activityByMonth);
    }


}
