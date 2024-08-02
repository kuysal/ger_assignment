package com.cvsreader.csvreader.CsvModel;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="csv")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CsvModel {

    @Id
    private String code;

    @ElementCollection
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "attribute_value")
    private Map<String, String> attributes = new HashMap<>();
}
