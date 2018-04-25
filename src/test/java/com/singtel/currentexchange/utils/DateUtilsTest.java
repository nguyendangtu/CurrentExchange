package com.singtel.currentexchange.utils;

import com.singtel.currentexchange.constants.Constants;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void getBusinessDays() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
        String startDate = "2017-01-01";
        String endDate = "2017-03-01";
        LocalDate localStartDate = LocalDate.parse(startDate, formatter);
        LocalDate localEndDate = LocalDate.parse(endDate, formatter);
        List<LocalDate> output = DateUtils.getBusinessDays(localStartDate, localEndDate);
        Assert.assertEquals(60, output.size());
    }
}