package com.tmdna.mbeer.repository;

import com.tmdna.mbeer.model.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveBeerTest() {
        final String beerName = "testBeerName";

        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName(beerName)
                .build());

        assertNotNull(savedBeer);
        assertNotNull(savedBeer.getId());
        assertEquals(beerName, savedBeer.getBeerName());
    }
}