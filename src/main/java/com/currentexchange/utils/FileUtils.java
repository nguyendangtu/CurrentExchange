package com.singtel.currentexchange.utils;

import com.singtel.currentexchange.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

public class FileUtils {
    private static final Logger logger = LoggerFactory
            .getLogger(FileUtils.class);

    public static String getFilePath(String rootPath, LocalDate businessDate) {
        logger.info("get file path with root path {} and business date {}", rootPath, businessDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
        StringBuffer path = new StringBuffer(rootPath)
                .append("/")
                .append(businessDate.getYear())
                .append("/")
                .append(businessDate.format(formatter))
                .append(Constants.SUFFIX_TXT);
        return path.toString();
    }

    public static TreeMap<String, String> getRates(String fileName) throws Exception {
        logger.info("get rates from file name {}", fileName);
        TreeMap<String, String> rates = new TreeMap<>();

        InputStream inputStream = FileUtils.class.getResourceAsStream(fileName);
        if (inputStream != null) {//read file into jar file
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    String cells[] = line.split(" ");
                    if (cells.length > 4) {
                        rates.put(cells[1], cells[4]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("getRates::" + e.getMessage());
            }
        } else { //read file from folder config
            try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                stream.forEach(line -> {
                    String cells[] = line.split(" ");
                    if (cells.length > 4) {
                        rates.put(cells[1], cells[4]);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("getRates::" + e.getMessage());
                throw e;
            }
        }

        return rates;
    }

    public static List<String> getFilePaths(String rootPath, List<LocalDate> businessDays) {
        List<String> filePaths = new ArrayList<>();
        businessDays.forEach(businessDay -> filePaths.add(getFilePath(rootPath, businessDay)));
        return filePaths;
    }
}
