package com.projet.project_e_banking.Service.EspaceClient;


import com.projet.project_e_banking.Dto.EspaceClient.TransactionDto;
import com.projet.project_e_banking.Model.EspaceClient.Transaction;
import com.projet.project_e_banking.Repository.EspaceClient.TransactionRepository;
import com.projet.project_e_banking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

  public List<TransactionDto> getTransactionByUserId(Long userId) {
    List<Transaction> transactions =  transactionRepository.findByUserId(userId);
      return transactions.stream().map(t -> {
          TransactionDto dto = new TransactionDto();
          dto.setId(t.getId());
          dto.setAmount(t.getAmount());
          dto.setType(t.getType());

          dto.setTimestamp(t.getTimestamp());
          dto.setAccountId(t.getAccount().getId());
          dto.setUserId(t.getUser().getId());
          dto.setStatus(t.getStatus());

          return dto;
      }).collect(Collectors.toList());
  }

      public List<TransactionDto> getTransactionByAccountId(Long accountId) {
      List<Transaction> transactions =  transactionRepository.findByAccountId(accountId);
      return transactions.stream().map(t -> {
          TransactionDto dto = new TransactionDto();
          dto = this.Todto(t);
          return dto;
              }).collect(Collectors.toList());
      }
         public List<TransactionDto>getRecentTransactions(Long accountId) {
         List<Transaction> transactions =  transactionRepository.findTop5ByAccountIdOrderByTimestampDesc(accountId);

          return transactions.stream().map(t -> {
              TransactionDto dto = new TransactionDto();
              dto = this.Todto(t);
              return dto;
          }).collect(Collectors.toList());
      }

  TransactionDto Todto(Transaction transaction) {
      TransactionDto dto = new TransactionDto();
      dto.setId(transaction.getId());
      dto.setAmount(transaction.getAmount());
      dto.setType(transaction.getType());
      dto.setTimestamp(transaction.getTimestamp());
      dto.setAccountId(transaction.getAccount().getId());
      dto.setUserId(transaction.getUser().getId());
      dto.setStatus(transaction.getStatus());
      return dto;
  }
}
