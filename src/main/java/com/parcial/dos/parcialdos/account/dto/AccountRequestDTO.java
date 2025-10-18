package com.parcial.dos.parcialdos.account.dto;

import java.math.BigDecimal;

public class AccountRequestDTO {
    private String numeroCuenta;
    private String dueno;
    private BigDecimal balanceActual;

    public AccountRequestDTO() {}

    public AccountRequestDTO(String numeroCuenta, String dueno, BigDecimal balanceActual) {
        this.numeroCuenta = numeroCuenta;
        this.dueno = dueno;
        this.balanceActual = balanceActual;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public BigDecimal getBalanceActual() {
        return balanceActual;
    }

    public void setBalanceActual(BigDecimal balanceActual) {
        this.balanceActual = balanceActual;
    }
}