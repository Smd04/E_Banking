package com.projet.project_e_banking.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoCurrencyDto {
    private String id;
    private String symbol;
    private String name;
    private String image;
    @JsonProperty("current_price")
    private double currentPrice;
    @JsonProperty("price_change_24h")
    private double priceChange24h;
    @JsonProperty("price_change_percentage_24h")
    private double priceChangePercentage24h;

    public CryptoCurrencyDto() {}

    public CryptoCurrencyDto(String id, String symbol, String name, String image,
                             double currentPrice, double priceChange24h,
                             double priceChangePercentage24h) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.image = image;
        this.currentPrice = currentPrice;
        this.priceChange24h = priceChange24h;
        this.priceChangePercentage24h = priceChangePercentage24h;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
    public double getPriceChange24h() { return priceChange24h; }
    public void setPriceChange24h(double priceChange24h) { this.priceChange24h = priceChange24h; }
    public double getPriceChangePercentage24h() { return priceChangePercentage24h; }
    public void setPriceChangePercentage24h(double priceChangePercentage24h) {
        this.priceChangePercentage24h = priceChangePercentage24h;
    }
}