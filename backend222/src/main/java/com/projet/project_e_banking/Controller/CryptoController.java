package com.projet.project_e_banking.Controller;

import com.projet.project_e_banking.Dto.CryptoCurrencyDto;
import com.projet.project_e_banking.Dto.CryptoTransactionDto;
import com.projet.project_e_banking.Service.DaoImpl.crypto.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/all/{userId}")
    public List<CryptoCurrencyDto> getAllCryptos(
            @PathVariable("userId") Long userId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "perPage", defaultValue = "10") int perPage) {
        return cryptoService.getAllCryptos(userId, page, perPage);
    }

    @GetMapping("/search/{userId}")
    public List<CryptoCurrencyDto> searchCryptos(@PathVariable("userId") Long userId,
                                                 @RequestParam String query,
                                                 @RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int perPage) {
        return cryptoService.searchCryptos(userId, query, page, perPage);
    }

    @GetMapping("/details/{userId}/{id}")
    public CryptoCurrencyDto getCryptoDetails(@PathVariable("userId") Long userId,
                                              @PathVariable("id") String id) {
        return cryptoService.getCryptoDetails(userId, id);
    }

    @PostMapping("/buy/{userId}")
    public ResponseEntity<?> buyCrypto(@PathVariable("userId") Long userId,
                                       @RequestBody CryptoTransactionDto transactionDto) {
        return cryptoService.buyCrypto(userId, transactionDto);
    }

    @PostMapping("/sell/{userId}")
    public ResponseEntity<?> sellCrypto(@PathVariable("userId") Long userId,
                                        @RequestBody CryptoTransactionDto transactionDto) {
        return cryptoService.sellCrypto(userId, transactionDto);
    }

    @GetMapping("/transactions/{userId}")
    public List<CryptoTransactionDto> getUserCryptoTransactions(@PathVariable("userId") Long userId) {
        return cryptoService.getUserCryptoTransactions(userId);
    }
}
