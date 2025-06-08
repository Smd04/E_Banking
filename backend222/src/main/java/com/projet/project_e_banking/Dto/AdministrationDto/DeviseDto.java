package com.projet.project_e_banking.Dto.AdministrationDto;

import java.math.BigDecimal;

public class DeviseDto {

        private Long id;
        private String code;
        private String name;
        private BigDecimal buyRate;
        private BigDecimal sellRate;
        private boolean enabled;
        private String symbol;
        private String country;
        private Long amount ;

        public DeviseDto() {
        }

    public DeviseDto(Long id, String code, String name, BigDecimal buyRate, BigDecimal sellRate, boolean enabled, String symbol, String country, Long amount) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.enabled = enabled;
        this.symbol = symbol;
        this.country = country;
        this.amount = amount;
    }

    public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
    }



