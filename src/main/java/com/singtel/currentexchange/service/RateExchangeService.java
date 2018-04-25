package com.singtel.currentexchange.service;

import com.singtel.currentexchange.bean.Rate;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public interface RateExchangeService {
    /**
     * Given a date, get the exchange rate of all the currencies (wrt USD)
     *
     * @param businessDate LocalDate
     * @return rate exchange of all currencies
     * @throws Exception
     */
    TreeMap<String, String> getUSDXRates(LocalDate businessDate) throws  Exception;

    /**
     * Given a date and 2 currencies, find the exchange rate between them
     *
     * @param businessDate   LocalDate
     * @param firstCurrency  String
     * @param secondCurrency String
     * @return the exchange between firstCurrency and the secondCurrency on this business day.
     * @throws Exception
     */
    Double getXRatesBy2CCY(LocalDate businessDate, String firstCurrency, String secondCurrency) throws Exception;

    /**
     * Given a date range and a given currency, find the exchange rate
     * of that currency (wrt USD) for the entire date range
     *
     * @param startDate LocalDate
     * @param endDate   LocalDate
     * @param currency  String
     * @return list of Rate(date,currency,rate) from start date to end date.
     * @throws Exception
     */
    List<Rate> getUSDXRatesByRange(LocalDate startDate, LocalDate endDate, String currency) throws Exception;
}
