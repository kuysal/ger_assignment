package com.cvsreader.csvreader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DynamicColumnService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addColumn(String tableName, String columnName, String columnType) {
        String sql = String.format("ALTER TABLE %s ADD COLUMN %s %s", tableName, columnName, columnType);
        jdbcTemplate.execute(sql);
    }
}