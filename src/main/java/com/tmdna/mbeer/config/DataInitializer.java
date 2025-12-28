package com.tmdna.mbeer.config;

import com.opencsv.bean.CsvToBeanBuilder;
import com.tmdna.mbeer.model.Beer;
import com.tmdna.mbeer.model.BeerCSVRecord;
import com.tmdna.mbeer.model.Customer;
import com.tmdna.mbeer.repository.BeerRepository;
import com.tmdna.mbeer.repository.CustomerRepository;
import com.tmdna.mbeer.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadCustomerData();
        loadBeerData();
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("Pale Ale")
                    .upd("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle("IPA")
                    .upd("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle("IPA")
                    .upd("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .build();

            beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("Customer 1")
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Customer 2")
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Customer 3")
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}