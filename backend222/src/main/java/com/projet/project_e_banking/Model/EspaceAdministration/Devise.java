package com.projet.project_e_banking.Model.EspaceAdministration;

import com.projet.project_e_banking.Model.EspaceBanque.Bank;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Devises")
public class Devise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // ex: USD, EUR, MAD

    @Column(nullable = false , unique = true)
    private String name;

    @Column(precision = 10, scale = 6, nullable = false)
    private BigDecimal buyRate;

    @Column(precision = 10, scale = 6, nullable = false)
    private BigDecimal sellRate;

    private boolean enabled = true; // actif ou désactivé

    private String symbol;// $, €, etc.

    @Column(nullable = false )
    private Long amount ;

    private String country;

    @ManyToMany(mappedBy = "devises")
    private List<Bank> banques;

    public Devise() {
    }

    public Devise(Long id, String code, String name, BigDecimal buyRate, BigDecimal sellRate, boolean enabled, String symbol, String country,Long amount) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.enabled = enabled;
        this.symbol = symbol;
        this.country = country;
        this.amount=amount ;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

//    public List<Bank> getBanques() {
//        return banques;
//    }
//
//    public void setBanques(List<Bank> banques) {
//        this.banques = banques;
//    }
}

