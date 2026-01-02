package com.tmdna.mbeer.service;

import com.tmdna.mbeer.model.BeerCSVRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

class BeerCsvServiceTest {

    BeerCsvService beerCsvService = new BeerCsvService();

    @Test
    void parseCsvTest() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> beers = beerCsvService.parseCsv(file);

        Assertions.assertThat(beers).isNotEmpty();
    }

}