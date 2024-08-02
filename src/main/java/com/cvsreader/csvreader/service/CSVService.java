package com.cvsreader.csvreader.service;

import com.cvsreader.csvreader.CsvModel.CsvModel;
import com.cvsreader.csvreader.helper.CSVHelper;
import com.cvsreader.csvreader.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    CsvRepository repository;

    public void save(MultipartFile file) {
        try {
            List<CsvModel> tutorials = CSVHelper.csvToModel(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }


    public List<CsvModel> getAllCsvs() {
        return repository.findAll();
    }
}