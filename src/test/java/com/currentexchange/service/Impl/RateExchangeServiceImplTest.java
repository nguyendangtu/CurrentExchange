package com.currentexchange.service.Impl;

import com.currentexchange.BaseTest;
import com.currentexchange.bean.Rate;
import com.currentexchange.constants.Constants;
import com.currentexchange.service.RateExchangeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeMap;

public class RateExchangeServiceImplTest extends BaseTest {

    @Autowired
    private RateExchangeService rateExchangeService;

    @Test
    public void getUSDXRates() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
        String businessDate = "2017-01-01";
        LocalDate localDate = LocalDate.parse(businessDate, formatter);
        TreeMap output = rateExchangeService.getUSDXRates(localDate);
        Assert.assertNotNull(output);
        Assert.assertEquals(7, output.size());
    }

    @Test
    public void getXRatesBy2CCY() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
        String businessDate = "2017-01-01";
        LocalDate localDate = LocalDate.parse(businessDate, formatter);
        String firstCurrency = "GBP";
        String secondCurrency = "OMR";
        Double output = rateExchangeService.getXRatesBy2CCY(localDate, firstCurrency, secondCurrency);
        Assert.assertEquals(new Double("0.5"), output);
    }

    @Test
    public void getUSDXRatesByRange() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
        String startDate = "2017-01-01";
        String endDate = "2017-01-03";
        String currency = "GBP";
        LocalDate localStartDate = LocalDate.parse(startDate, formatter);
        LocalDate localEndDate = LocalDate.parse(endDate, formatter);
        List<Rate> output = rateExchangeService.getUSDXRatesByRange(localStartDate, localEndDate, currency);
        Assert.assertNotNull(output);
        Assert.assertEquals(3, output.size());
        output.forEach(System.out::println);
    }
}