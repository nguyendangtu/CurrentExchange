package com.singtel.currentexchange.service.Impl;

import com.singtel.currentexchange.bean.Rate;
import org.junit.Assert;
import org.junit.Test;

public class RateCallableTest {

    @Test
    public void call() throws Exception {
        String fileName = "data/2017/2017-01-01.txt";
        String currency = "SGD";
        RateCallable rateCallable = new RateCallable(fileName, currency);
        Rate rate = rateCallable.call();
        Assert.assertNotNull(rate);
        System.out.println(rate);
        Assert.assertEquals("2017-01-01", rate.getDate());
        Assert.assertEquals("SGD", rate.getCurrency());
        Assert.assertEquals("0.74", rate.getRate());
    }
}