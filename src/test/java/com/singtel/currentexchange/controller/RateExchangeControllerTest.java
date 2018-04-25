package com.singtel.currentexchange.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.singtel.currentexchange.BaseTest;
import com.singtel.currentexchange.bean.Rate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RateExchangeControllerTest extends BaseTest {

    static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getUSDXRates() throws Exception {
        String url = "/rateExchange/getUSDXRates?businessDate=2017-01-01";
        MvcResult result = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        TreeMap<String, String> rates = mapper.readValue(result.getResponse().getContentAsString(), TreeMap.class);
        Assert.assertNotNull(rates);
        rates.forEach((k, v) -> System.out.println("key=" + k + ",value=" + v));
        Assert.assertEquals(7, rates.size());
    }

    @Test
    public void getUSDXRatesNegative() throws Exception {
        String url = "/rateExchange/getUSDXRates?businessDate=2917-01-01";
        MvcResult result = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        TreeMap<String, String> rates = mapper.readValue(result.getResponse().getContentAsString(), TreeMap.class);
        Assert.assertNotNull(rates);
        rates.forEach((k, v) -> System.out.println("key=" + k + ",value=" + v));
        Assert.assertEquals(1, rates.size());
    }

    @Test
    public void getXRatesBy2CCY() throws Exception {
        String url = "/rateExchange/getXRatesBy2CCY?businessDate=2017-01-01&firstCurrency=GBP&secondCurrency=OMR";
        MvcResult result = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Double rate = mapper.readValue(result.getResponse().getContentAsString(), Double.class);
        Assert.assertEquals(new Double("0.5"), rate);
    }

    @Test
    public void getXRatesBy2CCYNegative() throws Exception {
        String url = "/rateExchange/getXRatesBy2CCY?businessDate=2917-01-01&firstCurrency=GBP&secondCurrency=OMR";
        MvcResult result = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Double rate = mapper.readValue(result.getResponse().getContentAsString(), Double.class);
        Assert.assertEquals(new Double("0"), rate);

    }

    @Test
    public void getUSDXRatesByRange() throws Exception {
        String url = "/rateExchange/getUSDXRatesByRange?startDate=2017-01-01&endDate=2017-01-03&currency=SGD";
        MvcResult result = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<Rate> rates = mapper.readValue(
                result.getResponse().getContentAsString(),
                TypeFactory.defaultInstance().constructCollectionType(List.class, Rate.class));

        Assert.assertNotNull(rates);
        rates.forEach(System.out::println);
        Assert.assertEquals(3, rates.size());
    }
}
