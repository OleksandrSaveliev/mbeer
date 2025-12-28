package com.tmdna.mbeer.config;

import com.tmdna.mbeer.model.Beer;
import com.tmdna.mbeer.model.Customer;
import com.tmdna.mbeer.repository.BeerRepository;
import com.tmdna.mbeer.repository.CustomerRepository;
import com.tmdna.mbeer.service.BeerCsvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class DataInitializerTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @MockitoBean
    BeerCsvService csvService;

    DataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        dataInitializer = new DataInitializer(beerRepository, customerRepository, csvService);
    }

    @Test
    void testRun() throws Exception {
        dataInitializer.run();

        List<Beer> beerList = beerRepository.findAll();

        List<Customer> customers = customerRepository.findAll();

        assertEquals(3, beerList.size());
        assertEquals(3, customers.size());
    }
}