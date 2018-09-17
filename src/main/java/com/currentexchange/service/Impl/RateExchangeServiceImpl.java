package com.singtel.currentexchange.service.Impl;

import com.singtel.currentexchange.bean.Rate;
import com.singtel.currentexchange.service.RateExchangeService;
import com.singtel.currentexchange.utils.DateUtils;
import com.singtel.currentexchange.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service("rateExchangeService")
public class RateExchangeServiceImpl implements RateExchangeService {

    private static final Logger logger = LoggerFactory
            .getLogger(RateExchangeServiceImpl.class);

    @Value("${data.path}")
    private String dataPath;

    @Override
    @Cacheable(value = "getUSDXRates", key = "#businessDate")
    public TreeMap<String, String> getUSDXRates(LocalDate businessDate) throws Exception {
        String path = FileUtils.getFilePath(dataPath, businessDate);
        return FileUtils.getRates(path);
    }

    @Override
    @Cacheable(value = "getXRatesBy2CCY", key = "#businessDate + '-' +#firstCurrency + '-' + #secondCurrency")
    public Double getXRatesBy2CCY(LocalDate businessDate, String firstCurrency, String secondCurrency) throws Exception {
        TreeMap<String, String> rates = getUSDXRates(businessDate);
        Double firstRate = Double.parseDouble(rates.get(firstCurrency));
        Double secondRate = Double.parseDouble(rates.get(secondCurrency));
        Double rate = firstRate / secondRate;
        return rate;
    }

    @Override
    @Cacheable(value = "getUSDXRatesByRange", key = "#startDate + '-' + #endDate + '-' + #currency")
    public List<Rate> getUSDXRatesByRange(LocalDate startDate, LocalDate endDate, String currency) throws Exception {
        List<Rate> rates = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<LocalDate> businessDays = DateUtils.getBusinessDays(startDate, endDate);
        List<String> fileNames = FileUtils.getFilePaths(dataPath, businessDays);
        List<RateCallable> rateCallables = fileNames.stream().map(fileName -> new RateCallable(fileName, currency)).collect(Collectors.toList());
        try {
            List<Future<Rate>> results = service.invokeAll(rateCallables);
            for (Future<Rate> result : results) {
                rates.add(result.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("ERROR::getUSDXRatesByRange::invokeAll::RateCallable with error {}", e.toString());
            throw e;
        } finally {
            service.shutdown();
        }

        return rates;
    }
}
