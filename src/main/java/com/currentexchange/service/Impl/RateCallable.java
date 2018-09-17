package com.currentexchange.service.Impl;

import com.currentexchange.bean.Rate;
import com.currentexchange.utils.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TreeMap;
import java.util.concurrent.Callable;

public class RateCallable implements Callable<Rate> {
    Logger logger = LoggerFactory.getLogger(RateCallable.class);
    private String fileName;
    private String currency;

    public RateCallable(String fileName, String currency) {
        this.fileName = fileName;
        this.currency = currency;
        logger.info("RateCallable init with file name {} and currency {}", fileName, currency);
    }

    @Override
    public Rate call() throws Exception {
        try {
            TreeMap<String, String> rates = FileUtils.getRates(fileName);
            String rateValue = rates.get(currency);
            String date = FilenameUtils.getBaseName(fileName);
            return new Rate(date, currency, rateValue);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return new Rate(FilenameUtils.getBaseName(fileName), currency, "");
        }
    }
}
