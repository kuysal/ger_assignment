package com.cvsreader.csvreader.repository;

import com.cvsreader.csvreader.CsvModel.CsvModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvRepository extends JpaRepository<CsvModel, String> {
}
