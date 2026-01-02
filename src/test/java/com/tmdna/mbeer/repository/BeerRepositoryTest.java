package com.tmdna.mbeer.repository;

import com.tmdna.mbeer.config.DataInitializer;
import com.tmdna.mbeer.model.Beer;
import com.tmdna.mbeer.service.BeerCsvService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import({DataInitializer.class, BeerCsvService.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void shouldFindBeersWithNameContainingIpaIgnoreCase() {
        List<Beer> result = beerRepository.findAllByBeerNameContainingIgnoreCase("IPA");

        assertThat(result).isNotEmpty();
        assertThat(result)
                .allMatch(b ->
                        b.getBeerName().toLowerCase().contains("ipa"));
    }

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