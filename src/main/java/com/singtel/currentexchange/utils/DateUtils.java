package com.singtel.currentexchange.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    public static List<LocalDate> getBusinessDays(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> businessDays = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            businessDays.add(startDate);
            startDate = startDate.plusDays(1);
        }
        return businessDays;
    }
}
