package com.cvsreader.csvreader.helper;

import com.cvsreader.csvreader.CsvModel.CsvModel;
import com.cvsreader.csvreader.service.DynamicColumnService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "Title", "Description", "Published" };

    private static DynamicColumnService dynamicColumnService;

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<CsvModel> csvToModel(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<CsvModel> cvsModels = new ArrayList<CsvModel>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            Map<String, Integer> headers = csvParser.getHeaderMap();

            // Add columns to database
            headers.forEach((key, value) -> {
                if (!key.equals("code")) {
                    dynamicColumnService.addColumn("csv", key, "VARCHAR(255)");
                }
            });

            // Put data into database
            for (CSVRecord csvRecord : csvRecords) {
                CsvModel cvsModel = new CsvModel();
                cvsModel.setCode(csvRecord.get("code"));
                Map<String, String> attributes = new HashMap<>();
                headers.forEach((key, value) -> {
                    if (!key.equals("code")) {
                        attributes.put(key, csvRecord.get(key));
                    }
                });
                cvsModel.setAttributes(attributes);
                cvsModels.add(cvsModel);
            }

            return cvsModels;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


}