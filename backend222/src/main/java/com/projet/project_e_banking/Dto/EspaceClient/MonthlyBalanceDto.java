package com.projet.project_e_banking.Dto.EspaceClient;

public class MonthlyBalanceDto {
    Double revenues;
    Double depenses;

    public MonthlyBalanceDto() {
    }

    public MonthlyBalanceDto(Double revenues, Double depenses) {
        this.revenues = revenues;
        this.depenses = depenses;
    }

    public Double getRevenues() {
        return revenues;
    }

    public void setRevenues(Double revenues) {
        this.revenues = revenues;
    }

    public Double getDepenses() {
        return depenses;
    }

    public void setDepenses(Double depenses) {
        this.depenses = depenses;
    }
}
