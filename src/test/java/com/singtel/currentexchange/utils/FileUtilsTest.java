package com.singtel.currentexchange.utils;

import com.singtel.currentexchange.constants.Constants;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.NoSuchFileException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class FileUtilsTest {

    private static final String DATA_PATH = "data";

    @Test
    public void getFilePath() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
        String businessDate = "2017-01-01";
        LocalDate localDate = LocalDate.parse(businessDate, formatter);
        String output = FileUtils.getFilePath(DATA_PATH, localDate);
        Assert.assertEquals("data/2017/2017-01-01.txt", output);
    }

    @Test
    public void getRates() throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
        String businessDate = "2017-01-01";
        LocalDate localDate = LocalDate.parse(businessDate, formatter);
        String path = FileUtils.getFilePath(DATA_PATH, localDate);
        TreeMap<String, String> output = FileUtils.getRates(path);
        output.forEach((k, v) -> System.out.println("key:" + k + ",value:" + v));
        Assert.assertNotNull(output);
        Assert.assertEquals(7, output.size());
    }

    @Test(expected = NoSuchFileException.class)
    public void getRatesNoSuchFileException() throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
        String businessDate = "2917-01-01";
        LocalDate localDate = LocalDate.parse(businessDate, formatter);
        String path = FileUtils.getFilePath(DATA_PATH, localDate);
        FileUtils.getRates(path);
    }

    @Test
    public void getFilePaths() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
        String startDate = "2017-01-01";
        String endDate = "2017-01-03";
        LocalDate localStartDate = LocalDate.parse(startDate, formatter);
        LocalDate localEndDate = LocalDate.parse(endDate, formatter);
        List<LocalDate> businessDays = DateUtils.getBusinessDays(localStartDate, localEndDate);
        List<String> output = FileUtils.getFilePaths(DATA_PATH, businessDays);
        Assert.assertNotNull(output);
        output.forEach(System.out::println);
        Assert.assertEquals(3, output.size());
        Collections.sort(output);
        Assert.assertEquals("data/2017/2017-01-01.txt", output.get(0));
        Assert.assertEquals("data/2017/2017-01-02.txt", output.get(1));
        Assert.assertEquals("data/2017/2017-01-03.txt", output.get(2));
    }


}