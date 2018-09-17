package com.currentexchange.bean;

import java.io.Serializable;

public class Rate implements Serializable {
    private static final long serialVersionUID = -1L;
    private String date;
    private String currency;
    private String rate;

    public Rate() {
    }

    public Rate(String date, String currency, String rate) {
        this.date = date;
        this.currency = currency;
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "date=" + date + ", currency=" + currency + ", rate=" + rate;
    }
}
