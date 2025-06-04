package com.projet.project_e_banking.Service.DaoImpl.crypto;

import com.projet.project_e_banking.Dto.CryptoCurrencyDto;
import com.projet.project_e_banking.Dto.CryptoTransactionDto;
import com.projet.project_e_banking.Model.EspaceAdministration.CryptoTransaction;
import com.projet.project_e_banking.Model.EspaceAdministration.SystemSettings;
import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.CryptoTransactionRepository;
import com.projet.project_e_banking.Repository.SystemSettingsRepository;
import com.projet.project_e_banking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private static final String COINGECKO_API_BASE = "https://api.coingecko.com/api/v3";
    private final RestTemplate restTemplate;

    @Autowired
    private SystemSettingsRepository systemSettingsRepository;

    @Autowired
    private CryptoTransactionRepository cryptoTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CryptoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CryptoCurrencyDto> getAllCryptos(Long userId) {
        checkCryptoEnabled(userId);

        String url = COINGECKO_API_BASE + "/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false";
        ResponseEntity<CryptoCurrencyDto[]> response = restTemplate.getForEntity(url, CryptoCurrencyDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<CryptoCurrencyDto> searchCryptos(Long userId, String query) {
        checkCryptoEnabled(userId);

        List<CryptoCurrencyDto> allCryptos = getAllCryptos(userId);
        return allCryptos.stream()
                .filter(crypto -> crypto.getName().toLowerCase().contains(query.toLowerCase()) ||
                        crypto.getSymbol().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public CryptoCurrencyDto getCryptoDetails(Long userId, String id) {
        checkCryptoEnabled(userId);

        String url = COINGECKO_API_BASE + "/coins/" + id + "?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false";
        return restTemplate.getForObject(url, CryptoCurrencyDto.class);
    }

    public ResponseEntity<?> buyCrypto(Long userId, CryptoTransactionDto transactionDto) {
        try {
            checkCryptoEnabled(userId);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Here you should also check account balance, etc.

            CryptoTransaction transaction = new CryptoTransaction(
                    user,
                    transactionDto.getCryptoId(),
                    transactionDto.getCryptoSymbol(),
                    transactionDto.getCryptoName(),
                    transactionDto.getAmount(),
                    transactionDto.getPriceAtTransaction(),
                    "BUY"
            );

            cryptoTransactionRepository.save(transaction);

            return ResponseEntity.ok("Cryptocurrency purchased successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> sellCrypto(Long userId, CryptoTransactionDto transactionDto) {
        try {
            checkCryptoEnabled(userId);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if user has enough crypto to sell

            CryptoTransaction transaction = new CryptoTransaction(
                    user,
                    transactionDto.getCryptoId(),
                    transactionDto.getCryptoSymbol(),
                    transactionDto.getCryptoName(),
                    transactionDto.getAmount(),
                    transactionDto.getPriceAtTransaction(),
                    "SELL"
            );

            cryptoTransactionRepository.save(transaction);

            return ResponseEntity.ok("Cryptocurrency sold successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public List<CryptoTransactionDto> getUserCryptoTransactions(Long userId) {
        checkCryptoEnabled(userId);

        List<CryptoTransaction> transactions = cryptoTransactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(t -> new CryptoTransactionDto(
                        t.getCryptoId(),
                        t.getCryptoSymbol(),
                        t.getCryptoName(),
                        t.getAmount(),
                        t.getPriceAtTransaction(),
                        t.getTransactionType(),
                        t.getTransactionDate()
                ))
                .collect(Collectors.toList());
    }

    private void checkCryptoEnabled(Long userId) {
        SystemSettings settings = systemSettingsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User settings not found"));

        if (!settings.isCryptoEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Crypto services are not enabled for this user");
        }
    }
}