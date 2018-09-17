package com.singtel.currentexchange.controller;

import com.singtel.currentexchange.bean.Rate;
import com.singtel.currentexchange.constants.Constants;
import com.singtel.currentexchange.service.RateExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/rateExchange")
@CrossOrigin
public class RateExchangeController {

    private static final Logger logger = LoggerFactory.getLogger(RateExchangeController.class);

    @Autowired
    private RateExchangeService rateExchangeService;

    @RequestMapping(method = RequestMethod.GET, value = "/getUSDXRates")
    @Cacheable(value = "USDXRates", key = "#businessDate")
    public TreeMap<String, String> getUSDXRates(@RequestParam("businessDate") @DateTimeFormat(pattern = Constants.YYYY_MM_DD) LocalDate businessDate) {
        try {
            return rateExchangeService.getUSDXRates(businessDate);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            TreeMap<String, String> rates = new TreeMap<>();
            rates.put("CCY_ERROR", "Please try with another business day on format YYYY-MM-DD");
            return rates;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getXRatesBy2CCY")
    @Cacheable(value = "XRatesBy2CCY", key = "#businessDate + '-' +#firstCurrency + '-' + #secondCurrency")
    public Double getXRatesBy2CCY(@RequestParam("businessDate") @DateTimeFormat(pattern = Constants.YYYY_MM_DD) LocalDate businessDate,
                                  @RequestParam("firstCurrency") String firstCurrency,
                                  @RequestParam("secondCurrency") String secondCurrency) {
        try {
            return rateExchangeService.getXRatesBy2CCY(businessDate, firstCurrency, secondCurrency);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new Double(0);
        }
    }

    @Cacheable(value = "USDXRatesByRange", key = "#startDate + '-' + #endDate + '-' + #currency")
    @RequestMapping(method = RequestMethod.GET, value = "/getUSDXRatesByRange")
    public List<Rate> getUSDXRatesByRange(@RequestParam("startDate") @DateTimeFormat(pattern = Constants.YYYY_MM_DD) LocalDate startDate,
                                          @RequestParam("endDate") @DateTimeFormat(pattern = Constants.YYYY_MM_DD) LocalDate endDate,
                                          @RequestParam("currency") String currency) throws Exception {
        return rateExchangeService.getUSDXRatesByRange(startDate, endDate, currency);
    }

}
