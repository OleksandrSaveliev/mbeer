package com.tmdna.mbeer.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.tmdna.mbeer.model.BeerCSVRecord;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
@Primary
public class BeerCsvService implements CsvService<BeerCSVRecord> {
    @Override
    public List<BeerCSVRecord> parseCsv(File csvFile) {

        try (Reader reader = new FileReader(csvFile)) {
            CsvToBean<BeerCSVRecord> csvToBean = new CsvToBeanBuilder<BeerCSVRecord>(reader)
                    .withType(BeerCSVRecord.class)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
