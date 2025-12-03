package com.tmdna.mbeer.controller;

import com.tmdna.mbeer.dto.BeerDTO;
import com.tmdna.mbeer.exception.NotFoundException;
import com.tmdna.mbeer.model.Beer;
import com.tmdna.mbeer.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController controller;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void beerNotFoundTest() {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");

        assertThrows(NotFoundException.class, () -> controller.getBeer(id));
    }

    @Test
    void getBeerByIdTest() {
        Beer beer = beerRepository.findAll().getFirst();

        assertNotNull(beerRepository.findById(beer.getId()));
    }

    @Test
    void getBeerListTest() {
        List<BeerDTO> beerDTOS = controller.getBeers().getBody();

        assertNotNull(beerDTOS);
        assertEquals(3, beerDTOS.size());
    }

    @Rollback
    @Transactional
    @Test
    void emptyBeerListTest() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS = controller.getBeers().getBody();

        assertNotNull(beerDTOS);
        assertEquals(0, beerDTOS.size());
    }
}