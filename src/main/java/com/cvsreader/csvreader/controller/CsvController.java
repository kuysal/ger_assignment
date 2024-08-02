package com.cvsreader.csvreader.controller;

import com.cvsreader.csvreader.CsvModel.CsvModel;
import com.cvsreader.csvreader.helper.CSVHelper;
import com.cvsreader.csvreader.message.ResponseMessage;
import com.cvsreader.csvreader.repository.CsvRepository;
import com.cvsreader.csvreader.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CsvController {

    @Autowired
    CSVService fileService;

    @Autowired
    CsvRepository csvRepository;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @DeleteMapping("/deleteByCode/{code}")
    public  ResponseEntity<HttpStatus>  deleteByCode(String code) {
        try {
            csvRepository.deleteById(code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCsvData")
    public ResponseEntity<List<CsvModel>> getAllCsvData() {
        try {
            List<CsvModel> csvList = new ArrayList<>(csvRepository.findAll());

            if (csvList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(csvList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAllCsvData")
    public ResponseEntity<HttpStatus> deleteAllCsvData() {
        try {
            csvRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
